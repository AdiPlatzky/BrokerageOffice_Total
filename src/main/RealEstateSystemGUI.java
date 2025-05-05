package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import Decorator.*;
import Factory.*;
import Observer.Observer;
import Property.*;
import SearchStrategy.*;
import SystemPermissions.*;

/**
 * Class implementing a graphical user interface for the real estate system.
 * Provides visual demonstration of the various design patterns in the system.
 */
public class RealEstateSystemGUI extends JFrame {

    private JTabbedPane tabbedPane;
    private JTable propertiesTable;
    private DefaultTableModel tableModel;
    private JTextArea outputTextArea;
    private List<Property> allProperties = new ArrayList<>(); // Empty initialization as default

    // System users
    private User buyer;
    private User seller;
    private User broker;

    // Components for search
    private JComboBox<String> searchStrategyComboBox;
    private JTextField searchValueField;
    private JButton searchButton;

    // Components for decorator
    private JComboBox<String> propertyComboBox;
    private JCheckBox cleaningCheckbox;
    private JCheckBox haulageCheckbox;
    private JCheckBox designCheckbox;
    private JCheckBox guaranteeCheckbox;
    private JButton calculateDealButton;
    private JComboBox<String> decoratorPropertyComboBox;

    // Variables for Observer tab
    private JComboBox<String> observerPropertyComboBox;

    /**
     * Constructor - defines the graphical interface
     */
    public RealEstateSystemGUI() {
        // Basic window settings
        setTitle("Real Estate System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            // Set local system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create output area - this must come before initializeSystem()
        createOutputArea();

        // Initialize the system and load data
        initializeSystem();
        mainPanel.add(createOutputPanel(), BorderLayout.SOUTH);

        // Create tabs
        createTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add main panel to window
        add(mainPanel);

        // Refresh property table and combo boxes
        refreshPropertiesTable();
        refreshDecoratorPropertyList();
        refreshObserverPropertyList();
    }

    private void refreshObserverPropertyList() {
        observerPropertyComboBox.removeAllItems();
        for (Property p : allProperties) {
            observerPropertyComboBox.addItem(p.getAddress().toString());
        }
    }

    private void refreshDecoratorPropertyList() {
        decoratorPropertyComboBox.removeAllItems();
        for (Property p : allProperties) {
            decoratorPropertyComboBox.addItem(p.getId() + ": " + p.getAddress().toString());
        }
    }

    /**
     * Creates the tab system
     */
    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();

        // Add tabs for different design patterns
        tabbedPane.addTab("Properties", createPropertiesPanel());
        tabbedPane.addTab("Factory Method", createFactoryPanel());
        tabbedPane.addTab("Observer", createObserverPanel());
        tabbedPane.addTab("Strategy", createStrategyPanel());
        tabbedPane.addTab("Decorator", createDecoratorPanel());
    }

    /**
     * Creates the output area
     */
    private void createOutputArea() {
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);

        // Add welcome message
        appendToOutput("Welcome to the Real Estate System");
        appendToOutput("This system demonstrates 5 design patterns: Factory Method, Composite, Observer, Strategy, Decorator");
    }

    /**
     * Creates a panel for output display
     */
    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Output"));

        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setPreferredSize(new Dimension(700, 150));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> outputTextArea.setText(""));
        panel.add(clearButton, BorderLayout.EAST);

        return panel;
    }

    /**
     * Creates a panel to display properties in the system
     */
    private JPanel createPropertiesPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        // Create table model
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Address", "Area (sqm)", "Price/sqm", "Total Price", "Status", "Type"}
        );

        // Create table
        propertiesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(propertiesTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(e -> refreshPropertiesTable());
        buttonPanel.add(refreshButton);

        JButton detailsButton = new JButton("Show Details");
        detailsButton.addActionListener(e -> showSelectedPropertyDetails());
        buttonPanel.add(detailsButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates a panel to demonstrate the Factory Method pattern
     */
    private JPanel createFactoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel innerPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        innerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create buttons to display information about different users
        JButton buyerButton = new JButton("Show Buyer Info");
        buyerButton.addActionListener(e -> showUserInfo(buyer));
        innerPanel.add(buyerButton);

        JButton sellerButton = new JButton("Show Seller Info");
        sellerButton.addActionListener(e -> showUserInfo(seller));
        innerPanel.add(sellerButton);

        JButton brokerButton = new JButton("Show Broker Info");
        brokerButton.addActionListener(e -> showUserInfo(broker));
        innerPanel.add(brokerButton);

        panel.add(innerPanel, BorderLayout.NORTH);

        // Add explanation panel
        JTextArea explanationArea = new JTextArea(
                "The Factory Method pattern allows creating objects from different classes using a unified interface.\n" +
                        "In our system, the UserFactory class creates different types of users (buyer, seller, broker) according to the parameter passed.\n" +
                        "Advantage: No need to know the specific class - the code simply requests a user type and the factory creates the appropriate object."
        );
        explanationArea.setEditable(false);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        explanationArea.setBackground(new Color(240, 240, 240));

        panel.add(new JScrollPane(explanationArea), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates a panel to demonstrate the Observer pattern
     */
    private JPanel createObserverPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel demoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Create sample property list
        observerPropertyComboBox = new JComboBox<>();
        observerPropertyComboBox.setPreferredSize(new Dimension(200, 25));

        // Button to delete property
        JButton deleteButton = new JButton("Delete Property");
        deleteButton.addActionListener(e -> {
            if (observerPropertyComboBox.getSelectedIndex() != -1) {
                demonstrateObserver((String) observerPropertyComboBox.getSelectedItem());
            } else {
                appendToOutput("Please select a property to delete");
            }
        });

        demoPanel.add(new JLabel("Select property to delete:"));
        demoPanel.add(observerPropertyComboBox);
        demoPanel.add(deleteButton);

        panel.add(demoPanel, BorderLayout.NORTH);

        // Add explanation panel
        JTextArea explanationArea = new JTextArea(
                "The Observer pattern allows automatic updating of objects when another object changes.\n" +
                        "In our system, when a seller deletes a property, the broker receives an automatic update about the deletion.\n" +
                        "The seller is the Observable, and the broker is the Observer.\n" +
                        "Advantage: Loose coupling between objects - the seller doesn't need to know which specific brokers are listening to them."
        );
        explanationArea.setEditable(false);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        explanationArea.setBackground(new Color(240, 240, 240));

        panel.add(new JScrollPane(explanationArea), BorderLayout.CENTER);

        return panel;
    }

    private void demonstrateObserver(String selectedAddress) {
        Property propertyToDelete = null;

        // Find the property with the selected address
        for (Property p : allProperties) {
            if (p.getAddress().toString().equals(selectedAddress)) {
                propertyToDelete = p;
                break;
            }
        }

        if (propertyToDelete == null) {
            JOptionPane.showMessageDialog(this, "Property not found.");
            return;
        }

        // Simulate seller deleting property
        List<Property> tempList = new ArrayList<>(allProperties);
        try {
            ((Seller) seller).deleteProperty(tempList, propertyToDelete);

            // Update local list and refresh UI
            allProperties = tempList;
            refreshPropertiesTable();
            refreshDecoratorPropertyList();
            refreshObserverPropertyList();

            appendToOutput("Seller deleted property: " + selectedAddress);
            appendToOutput("Broker was notified about the deletion");

            JOptionPane.showMessageDialog(this, "Broker was updated about property deletion: " + selectedAddress);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    /**
     * Creates a panel to demonstrate the Strategy pattern
     */
    private JPanel createStrategyPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Search strategy selection
        searchStrategyComboBox = new JComboBox<>(new String[]{
                "Search by Status - FOR SALE",
                "Search by Status - SOLD",
                "Search by Price/sqm - Less Than",
                "Search by Price/sqm - Greater Than",
                "Search by Area Average - Below Average",
                "Search by Area Average - Above Average"
        });

        searchValueField = new JTextField(10);
        searchValueField.setEnabled(false);

        searchButton = new JButton("Search");

        topPanel.add(new JLabel("Select search strategy:"));
        topPanel.add(searchStrategyComboBox);
        topPanel.add(new JLabel("Value:"));
        topPanel.add(searchValueField);
        topPanel.add(searchButton);

        panel.add(topPanel, BorderLayout.NORTH);

        // Create table for search results
        DefaultTableModel searchResultModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Address", "Area (sqm)", "Price/sqm", "Total Price", "Status"}
        );

        JTable searchResultTable = new JTable(searchResultModel);
        JScrollPane resultScrollPane = new JScrollPane(searchResultTable);
        panel.add(resultScrollPane, BorderLayout.CENTER);

        // Add explanation panel
        JTextArea explanationArea = new JTextArea(
                "The Strategy pattern allows switching algorithms at runtime.\n" +
                        "In our system, you can choose different property search strategies: by status, price per sqm, or comparison to area average.\n" +
                        "Advantage: New search strategies can be added without changing existing code, and the client can switch strategies at runtime."
        );
        explanationArea.setEditable(false);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        explanationArea.setBackground(new Color(240, 240, 240));

        panel.add(explanationArea, BorderLayout.SOUTH);

        // Set up events
        searchStrategyComboBox.addActionListener(e -> {
            int index = searchStrategyComboBox.getSelectedIndex();
            // Enable value input only for price-based searches
            searchValueField.setEnabled(index == 2 || index == 3);
        });

        searchButton.addActionListener(e -> {
            // Implement search based on selected strategy
            List<Property> searchResults = performSearch();

            // Clear the table
            searchResultModel.setRowCount(0);

            // Fill the table with results
            for (Property property : searchResults) {
                searchResultModel.addRow(new Object[]{
                        property.getId(),
                        property.getAddress().toString(),
                        property.getSqm(),
                        property.getPricePerSqm(),
                        property.getTotalPrice(),
                        property.getStatus().toString()
                });
            }

            appendToOutput("Search completed: " + searchStrategyComboBox.getSelectedItem() + ", found " + searchResults.size() + " results");
        });

        return panel;
    }

    private List<Property> performSearch() {
        if (allProperties.isEmpty()) {
            return Collections.emptyList();
        }

        int strategyIndex = searchStrategyComboBox.getSelectedIndex();
        PropertySearchStrategy strategy = null;

        // Create appropriate strategy based on selection
        switch (strategyIndex) {
            case 0: // FOR_SALE
                strategy = new ByStatus(Status.FOR_SALE);
                break;
            case 1: // SOLD
                strategy = new ByStatus(Status.SOLD);
                break;
            case 2: // Price less than
                try {
                    double price = Double.parseDouble(searchValueField.getText());
                    strategy = new PriceBySqm(price, PriceBySqm.ComparisonType.LESS_THAN);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number for price");
                    return Collections.emptyList();
                }
                break;
            case 3: // Price greater than
                try {
                    double price = Double.parseDouble(searchValueField.getText());
                    strategy = new PriceBySqm(price, PriceBySqm.ComparisonType.GREATER_THEN);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number for price");
                    return Collections.emptyList();
                }
                break;
            case 4: // Below average
                strategy = new MeanPriceSqm(MeanPriceSqm.ComparisonToAverage.BELLOW_AVERAGE);
                break;
            case 5: // Above average
                strategy = new MeanPriceSqm(MeanPriceSqm.ComparisonToAverage.ABOVE_AVERAGE);
                break;
            default:
                return Collections.emptyList();
        }

        // Execute search using selected strategy
        PropertySearcher searcher = new PropertySearcher(strategy);
        return searcher.executeSearch(allProperties);
    }

    /**
     * Creates a panel to demonstrate the Decorator pattern
     */
    private JPanel createDecoratorPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel topPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        // Panel for property selection
        JPanel propertyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        decoratorPropertyComboBox = new JComboBox<>();
        propertyPanel.add(new JLabel("Select property:"));
        propertyPanel.add(decoratorPropertyComboBox);
        topPanel.add(propertyPanel);

        // Panel for add-on selection
        JPanel servicesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cleaningCheckbox = new JCheckBox("Cleaning");
        haulageCheckbox = new JCheckBox("Haulage");
        designCheckbox = new JCheckBox("Design");
        guaranteeCheckbox = new JCheckBox("Guarantee");

        servicesPanel.add(new JLabel("Select add-ons:"));
        servicesPanel.add(cleaningCheckbox);
        servicesPanel.add(haulageCheckbox);
        servicesPanel.add(designCheckbox);
        servicesPanel.add(guaranteeCheckbox);
        topPanel.add(servicesPanel);

        // Panel for deal calculation
        JPanel calculatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        calculateDealButton = new JButton("Calculate Deal");
        calculatePanel.add(calculateDealButton);
        topPanel.add(calculatePanel);

        panel.add(topPanel, BorderLayout.NORTH);

        // Add results panel
        JPanel resultPanel = new JPanel(new BorderLayout(5, 5));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Deal Result"));

        JTextArea dealResultArea = new JTextArea(5, 40);
        dealResultArea.setEditable(false);
        dealResultArea.setFont(new Font("Arial", Font.BOLD, 14));

        resultPanel.add(new JScrollPane(dealResultArea), BorderLayout.CENTER);
        panel.add(resultPanel, BorderLayout.CENTER);

        // Add explanation panel
        JTextArea explanationArea = new JTextArea(
                "The Decorator pattern allows adding functionality to an object at runtime, without changing the original class.\n" +
                        "In our system, the base deal is property purchase, and you can add additional services like cleaning, haulage, design, and guarantee.\n" +
                        "Advantage: Different services can be combined in various ways, without needing a separate class for each possible combination."
        );
        explanationArea.setEditable(false);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        explanationArea.setBackground(new Color(240, 240, 240));

        panel.add(explanationArea, BorderLayout.SOUTH);

        // Set up events
        calculateDealButton.addActionListener(e -> {
            if (decoratorPropertyComboBox.getSelectedIndex() != -1) {
                // Find the selected property
                String selectedPropertyString = (String) decoratorPropertyComboBox.getSelectedItem();
                int propertyId = Integer.parseInt(selectedPropertyString.split(":")[0].trim());

                Property selectedProperty = null;
                for (Property property : allProperties) {
                    if (property.getId() == propertyId) {
                        selectedProperty = property;
                        break;
                    }
                }

                if (selectedProperty != null) {
                    // Create basic deal
                    Deal deal = new BasicDeal(selectedProperty.getTotalPrice(),
                            "Purchase of property " + selectedProperty.getAddress() + " | ");

                    // Add services based on selection
                    if (cleaningCheckbox.isSelected()) {
                        deal = new Cleaning(deal);
                    }

                    if (haulageCheckbox.isSelected()) {
                        deal = new Haulage(deal);
                    }

                    if (designCheckbox.isSelected()) {
                        deal = new Design(deal);
                    }

                    if (guaranteeCheckbox.isSelected()) {
                        deal = new GuaranteeService(deal);
                    }

                    // Display final deal
                    dealResultArea.setText(
                            "Deal Description: " + deal.getDescription() + "\n\n" +
                                    "Property Price: " + selectedProperty.getTotalPrice() + " $\n\n" +
                                    "Final Price with Add-ons: " + deal.getCost() + " $"
                    );

                    appendToOutput("Deal calculated for property " + selectedProperty.getAddress() +
                            ", final price: " + deal.getCost() + " $");
                }
            } else {
                appendToOutput("Please select a property first");
            }
        });

        return panel;
    }

    /**
     * Initializes the system and loads data
     */
    private void initializeSystem() {
        // Create users
        buyer = UserFactory.createUser("Ronit", "1234", 1, UserRole.BUYER);
        seller = UserFactory.createUser("Moshe", "5678", 2, UserRole.SELLER);
        broker = UserFactory.createUser("Michal", "9012", 3, UserRole.BROKER);

        // Register broker to listen to seller actions
        ((Seller) seller).registerObserver((Observer) broker);

        // Load properties
        try {
            allProperties = PropertyFileReader.readPropertiesFromCSV();
            appendToOutput("System loading completed. Loaded " + allProperties.size() + " properties.");
        } catch (Exception e) {
            appendToOutput("Error loading properties: " + e.getMessage());
            e.printStackTrace();
            // In case of error, create sample properties
            createSampleProperties();
        }
    }

    /**
     * Creates sample properties in case of loading error
     */
    private void createSampleProperties() {
        appendToOutput("Creating sample properties...");

        // Create simple properties
        Address address1 = new Address("2 3");
        SimpleProperty property1 = new SimpleProperty(101, address1, 80, 9375, Status.FOR_SALE);
        allProperties.add(property1);

        Address address2 = new Address("4 5");
        SimpleProperty property2 = new SimpleProperty(102, address2, 60, 10000, Status.FOR_SALE);
        allProperties.add(property2);

        Address address3 = new Address("6 7");
        SimpleProperty property3 = new SimpleProperty(103, address3, 45, 8889, Status.SOLD);
        allProperties.add(property3);

        // Create composite property
        Address address4 = new Address("8 9");
        CompositeProperty property4 = new CompositeProperty(104, address4, Status.FOR_SALE);

        Address subAddress1 = new Address("8 9 1");
        SimpleProperty subProperty1 = new SimpleProperty(105, subAddress1, 40, 9500, Status.FOR_SALE);

        Address subAddress2 = new Address("8 9 2");
        SimpleProperty subProperty2 = new SimpleProperty(106, subAddress2, 35, 9714, Status.FOR_SALE);

        property4.add(subProperty1);
        property4.add(subProperty2);

        allProperties.add(property4);

        appendToOutput("Created " + allProperties.size() + " sample properties.");
    }

    /**
     * Adds a message to the output area
     */
    private void appendToOutput(String message) {
        outputTextArea.append("\n> " + message);
        // Scroll to end of text
        outputTextArea.setCaretPosition(outputTextArea.getDocument().getLength());
    }

    /**
     * Refreshes the properties table
     */
    private void refreshPropertiesTable() {
        // Clear the table
        tableModel.setRowCount(0);

        // If no properties, nothing to display
        if (allProperties == null || allProperties.isEmpty()) {
            appendToOutput("No properties to display");
            return;
        }

        // Fill the table with properties
        for (Property property : allProperties) {
            String type = property instanceof CompositeProperty ? "Composite Property" : "Simple Property";

            tableModel.addRow(new Object[]{
                    property.getId(),
                    property.getAddress().toString(),
                    property.getSqm(),
                    property.getPricePerSqm(),
                    property.getTotalPrice(),
                    property.getStatus().toString(),
                    type
            });
        }

        appendToOutput("Property list updated");
    }

    /**
     * Displays details about the selected property
     */
    private void showSelectedPropertyDetails() {
        int selectedRow = propertiesTable.getSelectedRow();
        if (selectedRow != -1) {
            int propertyId = (int) tableModel.getValueAt(selectedRow, 0);

            // Find the corresponding property
            for (Property property : allProperties) {
                if (property.getId() == propertyId) {
                    StringBuilder details = new StringBuilder();
                    details.append("Property Details #").append(propertyId).append(":\n");
                    details.append("Address: ").append(property.getAddress()).append("\n");
                    details.append("Area: ").append(property.getSqm()).append(" sqm\n");
                    details.append("Price/sqm: ").append(property.getPricePerSqm()).append(" $\n");
                    details.append("Total Price: ").append(property.getTotalPrice()).append(" $\n");
                    details.append("Status: ").append(property.getStatus()).append("\n");

                    if (property instanceof CompositeProperty) {
                        CompositeProperty composite = (CompositeProperty) property;
                        List<Property> subProperties = composite.getSubProperties();

                        details.append("\nSub-properties (").append(subProperties.size()).append("):\n");
                        for (Property subProperty : subProperties) {
                            details.append("- ").append(subProperty.getAddress())
                                    .append(", ").append(subProperty.getSqm()).append(" sqm, ")
                                    .append(subProperty.getTotalPrice()).append(" $\n");
                        }
                    }

                    JOptionPane.showMessageDialog(this, details.toString(),
                            "Property Details", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        } else {
            appendToOutput("Please select a property to display details");
        }
    }

    /**
     * Displays information about a user
     */
    private void showUserInfo(User user) {
        StringBuilder info = new StringBuilder();
        info.append("User Details #").append(user.getId()).append(":\n");
        info.append("Username: ").append(user.getUserName()).append("\n");
        info.append("Role: ").append(user.getCurrentRole());

        JOptionPane.showMessageDialog(this, info.toString(), "User Information", JOptionPane.INFORMATION_MESSAGE);
        appendToOutput("Displayed information for " + user.getUserName() + " (" + user.getCurrentRole() + ")");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RealEstateSystemGUI gui = new RealEstateSystemGUI();
            gui.setVisible(true);
        });
    }
}