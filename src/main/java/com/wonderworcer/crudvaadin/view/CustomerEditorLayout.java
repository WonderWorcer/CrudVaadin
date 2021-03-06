package com.wonderworcer.crudvaadin.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.wonderworcer.crudvaadin.repo.CustomerRepository;
import com.wonderworcer.crudvaadin.model.Customer;
import com.wonderworcer.crudvaadin.model.CustomerSex;
import com.wonderworcer.crudvaadin.service.CustomerService;
import com.wonderworcer.crudvaadin.service.CustomerServiceV2;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class CustomerEditorLayout extends VerticalLayout implements KeyNotifier {

    private final CustomerRepository repository;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerServiceV2 customerServiceV2;

    private Customer customer;
    private TextField name = new TextField("name");
    private ComboBox<CustomerSex> sex = new ComboBox<>("sex");
    private DatePicker birthdate = new DatePicker("birthday");

    /* Action buttons */
    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<Customer> binder = new Binder<>(Customer.class);
    private ChangeHandler changeHandler;

    @Autowired
    public CustomerEditorLayout(CustomerRepository repository) {
        this.repository = repository;
        add(name, sex, birthdate, actions);
        sex.setItems(CustomerSex.values());
        binder.bindInstanceFields(this);
        // Configure and style components
        setSpacing(true);
        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");
        addKeyPressListener(Key.ENTER, e -> save());
        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCustomer(customer));
        setVisible(false);
    }

    void delete() {
        customerServiceV2.delete(customer);
        changeHandler.onChange();
    }

    void save() {
        customerServiceV2.save(customer);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editCustomer(Customer c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            customer = repository.findById(c.getId()).get();
        } else {
            customer = c;
        }
        cancel.setVisible(persisted);
        binder.setBean(customer);
        setVisible(true);
        // Focus first name initially
        name.focus();
    }

   public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

}