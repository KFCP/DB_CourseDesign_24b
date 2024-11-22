import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionTest {
    public static void main(String[] args) {
        // 数据库连接URL
        String url = "jdbc:mysql://localhost:3306/employee_management_db";
        // 数据库用户名
        String username = "root";
        // 数据库密码
        String password = "password";

        try {
            // 加载MySQL驱动程序（在较新的JDBC版本中可能不需要这一步，因为驱动会自动加载）
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            Connection connection = DriverManager.getConnection(url, username, password);

            if (connection!= null) {
                System.out.println("成功连接到MySQL数据库！");
                // 在这里可以进行后续的数据库操作，比如执行查询等
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("找不到MySQL驱动程序类。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("连接MySQL数据库时出错：");
            e.printStackTrace();
        }
    }
}

//不用salt的注册方法
/*
class AddEmployeeWindow extends JFrame {
    private JTextField idField, nameField, deptField, jobField, emailField;
    private JPasswordField passField;
    private JButton addButton;

    public AddEmployeeWindow() {
        setTitle("增加新员工");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        idField = new JTextField();
        nameField = new JTextField();
        deptField = new JTextField();
        jobField = new JTextField();
        emailField = new JTextField();
        passField = new JPasswordField();
        addButton = new JButton("添加员工");

        add(new JLabel("员工号:"));
        add(idField);
        add(new JLabel("姓名:"));
        add(nameField);
        add(new JLabel("部门:"));
        add(deptField);
        add(new JLabel("职务:"));
        add(jobField);
        add(new JLabel("电子邮件:"));
        add(emailField);
        add(new JLabel("密码:"));
        add(passField);
        add(new JLabel());
        add(addButton);

        addButton.addActionListener(e -> addEmployee());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addEmployee() {
        String id = idField.getText();
        String name = nameField.getText();
        String dept = deptField.getText();
        String job = jobField.getText();
        String email = emailField.getText();
        String password = new String(passField.getPassword());

        // 直接使用加密模块对密码进行加密，不使用盐值
        String hashedPassword = EncryptionModule.hashPassword(password);

        String url = "jdbc:mysql://localhost:3306/yourDB"; // 修改为你的数据库地址
        String username = "yourUsername"; // 数据库用户名
        String dbPassword = "yourPassword"; // 数据库密码

        String insertQuery = "INSERT INTO PERSON (ID, NAME, DEPARTMENT, JOB, EMAIL, PASSWD) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, dept);
            stmt.setString(4, job);
            stmt.setString(5, email);
            stmt.setString(6, hashedPassword);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "员工已成功添加！");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "添加员工失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
*/


//可行的完整代码