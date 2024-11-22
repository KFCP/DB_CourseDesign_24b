import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PersonnelManagementSystem {

    public static void main(String[] args) {
        new MainWindow();
    }
}

// 加密模块类
class EncryptionModule {

    // 生成随机盐值
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // 使用 SHA-256 加盐哈希加密
    public static String hashPassword(String password, String salt) {
        String generatedHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes()); // 添加盐值
            byte[] bytes = md.digest(password.getBytes());
            generatedHash = Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedHash;
    }

    // 验证密码
    public static boolean verifyPassword(String inputPassword, String storedHash, String storedSalt) {
        String newHash = hashPassword(inputPassword, storedSalt);
        return newHash.equals(storedHash);
    }
}

// 1. 主窗口
class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("人事管理系统");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton addEmployeeButton = new JButton("增加新员工");
        JButton personnelChangeButton = new JButton("人事变动");
        JButton editEmployeeButton = new JButton("员工信息查询修改");
        JButton encryptionButton = new JButton("加密模块(已集成进系统)");
        JButton loginButton = new JButton("登录");

        addEmployeeButton.addActionListener(e -> new AddEmployeeWindow());// 2. 增加新员工窗口
        loginButton.addActionListener(e -> new LoginWindow());// 3. 登录窗口
        editEmployeeButton.addActionListener(e -> new EditEmployeeWindow());// 4. 员工信息查询修改窗口
        personnelChangeButton.addActionListener(e -> new PersonnelChangeWindow());// 5. 人事变动窗口
        encryptionButton.addActionListener(e -> new EncryptionModuleWindow());// 6. 加密模块提示窗口


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.add(addEmployeeButton);
        panel.add(personnelChangeButton);
        panel.add(editEmployeeButton);
        panel.add(encryptionButton);
        panel.add(loginButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

// 2. 增加新员工窗口
class AddEmployeeWindow extends JFrame {
    private JTextField idField, nameField, birthdayField, sexField, specialtyField, edu_levelField, deptField, authorityField, jobField, emailField, addressfield, telField, stateField, remarkField;
    private JPasswordField passField;
    private JButton addButton;

    public AddEmployeeWindow() {
        setTitle("增加新员工，标有*的为必填项");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // 使用BorderLayout分区布局

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 4, 10, 10)); // 设置布局

        idField = new JTextField();
        nameField = new JTextField();
        birthdayField = new JTextField();
        sexField = new JTextField();
        edu_levelField = new JTextField();
        specialtyField = new JTextField();
        deptField = new JTextField();
        authorityField = new JTextField();
        jobField = new JTextField();
        telField = new JTextField();
        emailField = new JTextField();
        addressfield = new JTextField();
        passField = new JPasswordField();
        stateField = new JTextField();
        remarkField = new JTextField();
        addButton = new JButton("添加员工");

        formPanel.add(new JLabel("*员工号:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("*姓名:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("*生日:"));
        formPanel.add(birthdayField);
        formPanel.add(new JLabel("*性别:"));
        formPanel.add(sexField);
        formPanel.add(new JLabel("*教育程度(CODE):"));
        formPanel.add(edu_levelField);
        formPanel.add(new JLabel("专业:"));
        formPanel.add(specialtyField);
        formPanel.add(new JLabel("*部门:"));
        formPanel.add(deptField);
        formPanel.add(new JLabel("*用户权限(CODE):"));
        formPanel.add(authorityField);
        formPanel.add(new JLabel("职务:"));
        formPanel.add(jobField);
        formPanel.add(new JLabel("电话:"));
        formPanel.add(telField);
        formPanel.add(new JLabel("电子邮件:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("家庭住址:"));
        formPanel.add(addressfield);
        formPanel.add(new JLabel("*密码:"));
        formPanel.add(passField);
        formPanel.add(new JLabel("当前状态(T-员工、F-非员工):"));
        formPanel.add(stateField);
        formPanel.add(new JLabel("备注:"));
        formPanel.add(remarkField);
        formPanel.add(new JLabel());
        formPanel.add(addButton);

        // 将表单和按钮布局到窗口中
        add(formPanel, BorderLayout.CENTER); // 表单位置位于中心
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH); // 按钮部分位于底部

        // 添加按钮监听
        addButton.addActionListener(e -> addEmployee());

        setLocationRelativeTo(null); // 居中窗口
        setVisible(true);
    }

    private void addEmployee() {
        String id = idField.getText();
        String name = nameField.getText();
        String birthday = birthdayField.getText();
        String sex = sexField.getText();
        String edu_level = edu_levelField.getText();
        String specialty = specialtyField.getText();
        String dept = deptField.getText();
        String authority = authorityField.getText();
        String job = jobField.getText();
        String tel = telField.getText();
        String email = emailField.getText();
        String address = addressfield.getText();
        String password = new String(passField.getPassword());
        String state = stateField.getText();
        String remark = remarkField.getText();

        // 使用加密模块生成盐值和哈希密码
        String salt = EncryptionModule.generateSalt();
        String hashedPassword = EncryptionModule.hashPassword(password, salt);

        String url = "jdbc:mysql://localhost:3306/employee_management_db";
        String username = "root";
        String dbPassword = "password";

        String insertQuery = "INSERT INTO PERSON (ID, NAME, BIRTHDAY, SEX,EDU_LEVEL, SPECIALTY, DEPARTMENT, AUTHORITY, JOB, TEL, EMAIL, ADDRESS, PASSWORD, STATE, REMARK, SALT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, birthday);
            stmt.setString(4, sex);
            stmt.setString(5, edu_level);
            stmt.setString(6, specialty);
            stmt.setString(7, dept);
            stmt.setString(8, authority);
            stmt.setString(9, job);
            stmt.setString(10, tel);
            stmt.setString(11, email);
            stmt.setString(12, address);
            stmt.setString(13, hashedPassword);
            stmt.setString(14, state);
            stmt.setString(15, remark);
            stmt.setString(16, salt);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "员工已成功添加！");
                String insertSaltQuery = "INSERT INTO encryption_module (EMP_ID, ENCRYPT_KEY, SALT) VALUES (?, ?, ?)";
                try (PreparedStatement insertSaltStmt = conn.prepareStatement(insertSaltQuery)) {
                    insertSaltStmt.setString(1, id);
                    insertSaltStmt.setString(2, hashedPassword);
                    insertSaltStmt.setString(3, salt);
                    insertSaltStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "添加员工失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

// 3. 登录窗口
class LoginWindow extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginWindow() {
        setTitle("登录");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        userField = new JTextField();
        passField = new JPasswordField();
        loginButton = new JButton("登录");

        add(new JLabel("员工号:"));
        add(userField);
        add(new JLabel("密码:"));
        add(passField);
        add(new JLabel());
        add(loginButton);

        loginButton.addActionListener(e -> authenticateUser());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void authenticateUser() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        String url = "jdbc:mysql://localhost:3306/employee_management_db";
        String dbUsername = "root";
        String dbPassword = "password";

        String selectQuery = "SELECT PASSWORD, SALT FROM PERSON WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {

            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                String storedHash = result.getString("PASSWORD");
                String storedSalt = result.getString("SALT");

                // 验证密码
                if (EncryptionModule.verifyPassword(password, storedHash, storedSalt)) {
                    JOptionPane.showMessageDialog(this, "登录成功！");
                } else {
                    JOptionPane.showMessageDialog(this, "用户名或密码错误！");
                }
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误！");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "登录失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

// 4. 员工信息查询修改窗口
class EditEmployeeWindow extends JFrame {
    private JTextField idField, nameField, birthdayField, sexField, specialtyField, edu_levelField, deptField, authorityField, jobField, emailField, addressfield, telField, stateField;
    private JPasswordField passField;
    private JButton searchButton, updateButton;
    private String employeeId;

    public EditEmployeeWindow() {
        setTitle("员工信息查询修改");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));

        idField = new JTextField();
        nameField = new JTextField();
        birthdayField = new JTextField();
        sexField = new JTextField();
        edu_levelField = new JTextField();
        specialtyField = new JTextField();
        deptField = new JTextField();
        authorityField = new JTextField();
        jobField = new JTextField();
        telField = new JTextField();
        emailField = new JTextField();
        addressfield = new JTextField();
        passField = new JPasswordField();
        stateField = new JTextField();

        searchButton = new JButton("查询");
        updateButton = new JButton("修改");

        // 默认禁用所有输入框，只有查询后启用
        nameField.setEnabled(false);
        birthdayField.setEnabled(false);
        sexField.setEnabled(false);
        edu_levelField.setEnabled(false);
        specialtyField.setEnabled(false);
        deptField.setEnabled(false);
        authorityField.setEnabled(false);
        jobField.setEnabled(false);
        telField.setEnabled(false);
        emailField.setEnabled(false);
        addressfield.setEnabled(false);
        passField.setEnabled(false);
        stateField.setEnabled(false);
        updateButton.setEnabled(false);

        add(new JLabel("员工号:"));
        add(idField);
        add(new JLabel("姓名:"));
        add(nameField);
        add(new JLabel("生日:"));
        add(birthdayField);
        add(new JLabel("性别:"));
        add(sexField);
        add(new JLabel("教育程度:"));
        add(edu_levelField);
        add(new JLabel("专业:"));
        add(specialtyField);
        add(new JLabel("部门:"));
        add(deptField);
        add(new JLabel("用户权限:"));
        add(authorityField);
        add(new JLabel("职务:"));
        add(jobField);
        add(new JLabel("电话:"));
        add(telField);
        add(new JLabel("电子邮件:"));
        add(emailField);
        add(new JLabel("家庭住址:"));
        add(addressfield);
        add(new JLabel("密码:"));
        add(passField);
        add(new JLabel("当前状态(T-员工、F-非员工):"));
        add(stateField);
        add(new JLabel());
        add(searchButton);
        add(new JLabel());
        add(updateButton);

        // 查询按钮
        searchButton.addActionListener(e -> searchEmployee());
        // 修改按钮
        updateButton.addActionListener(e -> updateEmployee());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void searchEmployee() {
        employeeId = idField.getText().trim();
        if (employeeId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入员工号！");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/employee_management_db";
        String dbUsername = "root";
        String dbPassword = "password";

        String selectQuery = "SELECT NAME, BIRTHDAY, SEX, EDU_LEVEL, SPECIALTY, DEPARTMENT, AUTHORITY, JOB, TEL, EMAIL, ADDRESS, PASSWORD, STATE, SALT FROM PERSON WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {

            stmt.setString(1, employeeId);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                // 显示查询到的信息
                nameField.setText(result.getString("NAME"));
                birthdayField.setText(result.getString("BIRTHDAY"));
                sexField.setText(result.getString("SEX"));
                edu_levelField.setText(result.getString("EDU_LEVEL"));
                specialtyField.setText(result.getString("SPECIALTY"));
                deptField.setText(result.getString("DEPARTMENT"));
                authorityField.setText(result.getString("AUTHORITY"));
                jobField.setText(result.getString("JOB"));
                telField.setText(result.getString("TEL"));
                emailField.setText(result.getString("EMAIL"));
                addressfield.setText(result.getString("ADDRESS"));
                stateField.setText(result.getString("STATE"));
                passField.setText(""); // 清空密码

                // 启用修改功能
                nameField.setEnabled(true);
                birthdayField.setEnabled(true);
                sexField.setEnabled(true);
                edu_levelField.setEnabled(true);
                specialtyField.setEnabled(true);
                deptField.setEnabled(true);
                authorityField.setEnabled(true);
                jobField.setEnabled(true);
                telField.setEnabled(true);
                emailField.setEnabled(true);
                addressfield.setEnabled(true);
                passField.setEnabled(true);
                stateField.setEnabled(true);
                updateButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "未找到员工信息！");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "查询失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateEmployee() {
        String name = nameField.getText().trim();
        String birthday = birthdayField.getText().trim();
        String sex = sexField.getText().trim();
        String edu_level = edu_levelField.getText().trim();
        String specialty = specialtyField.getText().trim();
        String dept = deptField.getText().trim();
        String authority = authorityField.getText().trim();
        String job = jobField.getText().trim();
        String tel = telField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressfield.getText().trim();
        String state = stateField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (authority.isEmpty() || name.isEmpty() || sex.isEmpty() || dept.isEmpty() || state.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写所有带*的字段！");
            return;
        }

        // 如果密码有修改，则进行加密
        String hashedPassword = password.isEmpty() ? null : EncryptionModule.hashPassword(password, EncryptionModule.generateSalt());

        String url = "jdbc:mysql://localhost:3306/employee_management_db";
        String dbUsername = "root";
        String dbPassword = "password";

        String updateQuery = "UPDATE PERSON SET NAME = ?, BIRTHDAY = ?, SEX = ?, EDU_LEVEL = ?, SPECIALTY = ?, DEPARTMENT = ?, AUTHORITY = ?, JOB = ?, TEL = ?, EMAIL = ?, ADDRESS = ?, STATE=?, PASSWORD = ? WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, name);
            stmt.setString(2, birthday);
            stmt.setString(3, sex);
            stmt.setString(4, edu_level);
            stmt.setString(5, specialty);
            stmt.setString(6, dept);
            stmt.setString(7, authority);
            stmt.setString(8, job);
            stmt.setString(9, tel);
            stmt.setString(10, email);
            stmt.setString(11, address);
            stmt.setString(12, state);
            if (hashedPassword != null) {
                stmt.setString(13, hashedPassword);
            } else {
                stmt.setNull(13, Types.VARCHAR);
            }
            stmt.setString(14, employeeId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "员工信息已成功更新！");
                String updateSaltQuery = "UPDATE encryption_module SET ENCRYPT_KEY = ? WHERE EMP_ID = ?";
                try (PreparedStatement updateSaltStmt = conn.prepareStatement(updateSaltQuery)) {
                    updateSaltStmt.setString(1, hashedPassword);
                    updateSaltStmt.setString(2, employeeId);
                    updateSaltStmt.executeUpdate();
                }
            } else {
                JOptionPane.showMessageDialog(this, "员工信息更新无效！");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "更新失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}

// 5. 人事变动窗口
class PersonnelChangeWindow extends JFrame {
    private JTextField idField, deptField, jobField;
    private JButton updateButton, deleteButton;
    private int num = 0; // 用于记录personnel表的ID

    public PersonnelChangeWindow() {
        setTitle("人事变动");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 1, 10, 10));

        idField = new JTextField();
        deptField = new JTextField();
        jobField = new JTextField();
        updateButton = new JButton("提交变动");
        deleteButton = new JButton("删除员工");

        // 添加组件到表单面板
        formPanel.add(createRow("员工号:", idField));
        formPanel.add(createRow("新部门:", deptField));
        formPanel.add(createRow("新职务:", jobField));

        // 按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10)); // 两个按钮并排
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        formPanel.add(buttonPanel);

        // 添加表单面板到窗口
        add(formPanel, BorderLayout.CENTER);

        updateButton.addActionListener(e -> handlePersonnelChange());
        deleteButton.addActionListener(e -> handleDeleteEmployee());

        // 初始化num
        initializeNum();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 初始化 num
    private void initializeNum() {
        String url = "jdbc:mysql://localhost:3306/employee_management_db";
        String username = "root";
        String dbPassword = "password";
        String maxIdQuery = "SELECT MAX(ID) AS MAX_ID FROM PERSONNEL";

        try (Connection conn = DriverManager.getConnection(url, username, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(maxIdQuery);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                num = rs.getInt("MAX_ID"); // 获取当前最大ID
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "初始化失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private JPanel createRow(String labelText, JTextField textField) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout(5, 5));
        rowPanel.add(new JLabel(labelText), BorderLayout.WEST);
        rowPanel.add(textField, BorderLayout.CENTER);
        return rowPanel;
    }

    // 人事变动处理
    private void handlePersonnelChange() {
        String id = idField.getText();
        String newDept = deptField.getText();
        String newJob = jobField.getText();

        if (id.isEmpty() || newDept.isEmpty() || newJob.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写所有字段！");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/employee_management_db";
        String username = "root";
        String dbPassword = "password";

        try (Connection conn = DriverManager.getConnection(url, username, dbPassword)) {
            conn.setAutoCommit(false); // 开启事务

            // 更新员工表
            String updateQuery = "UPDATE PERSON SET DEPARTMENT = ?, JOB = ? WHERE ID = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setString(1, newDept);
                updateStmt.setString(2, newJob);
                updateStmt.setString(3, id);

                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated == 0) {
                    JOptionPane.showMessageDialog(this, "员工ID不存在！");
                    conn.rollback(); // 回滚事务
                    return;
                }
            }

            // 记录变动信息
            num++; // 自增ID
            String recordQuery = "INSERT INTO PERSONNEL (ID, PERSON, PCHANGE, DESCRIPTION) VALUES (?, ?, ?, ?)";
            String description = "将部门更新为: " + newDept + ", 职务更新为: " + newJob;

            try (PreparedStatement recordStmt = conn.prepareStatement(recordQuery)) {
                recordStmt.setInt(1, num);
                recordStmt.setString(2, id);
                recordStmt.setString(3, "1"); // 1表示变动类型为修改
                recordStmt.setString(4, description);

                recordStmt.executeUpdate();
            }

            conn.commit(); // 提交事务
            JOptionPane.showMessageDialog(this, "人事变动已成功提交并记录！");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "操作失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    // 删除员工处理
    private void handleDeleteEmployee() {
        String id = idField.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写员工号！");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/employee_management_db";
        String username = "root";
        String dbPassword = "password";

        String selectQuery = "SELECT NAME FROM PERSON WHERE ID = ?";
        String deleteQuery = "DELETE FROM PERSON WHERE ID = ?";
        String insertRecordQuery = "INSERT INTO PERSONNEL (ID, PERSON, PCHANGE, DESCRIPTION) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, dbPassword)) {
            // 查询员工姓名
            String employeeName = null;
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, id);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        employeeName = rs.getString("NAME");
                    } else {
                        JOptionPane.showMessageDialog(this, "员工ID不存在！");
                        return; // 未找到员工，直接退出
                    }
                }
            }

            // 删除员工信息
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, id);

                int rowsDeleted = deleteStmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "员工已成功删除！");

                    // 记录删除操作
                    try (PreparedStatement recordStmt = conn.prepareStatement(insertRecordQuery)) {
                        int changeId = generateChangeId(conn);
                        String description = "员工" + employeeName + "已被删除";

                        recordStmt.setInt(1, changeId);
                        recordStmt.setString(2, id);
                        recordStmt.setString(3, "0"); // 0表示删除操作
                        recordStmt.setString(4, description);

                        recordStmt.executeUpdate();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "员工ID不存在！");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "删除失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private int generateChangeId(Connection conn) throws SQLException {
        String query = "SELECT MAX(ID) AS max_id FROM PERSONNEL";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            } else {
                return 1;
            }
        }
    }

}


//改进后的加密模块窗口提示
class EncryptionModuleWindow extends JFrame {
    private JTextField passwordField;
    private JButton generateButton;

    public EncryptionModuleWindow() {
        setTitle("加密模块提示");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 2, 10, 10));
        //generateButton = new JButton("确认");

        add(new JLabel("加密模块已集成进系统，保障您的每一次操作！"));

        add(new JLabel());
        //add(generateButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }


}

