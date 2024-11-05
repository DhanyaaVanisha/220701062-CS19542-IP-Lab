<!DOCTYPE html>
<html>
<head>
    <title>Update Employee</title>
</head>
<body>

    <?php
    include 'db.php';

    if (isset($_GET['EmpID'])) {
        $empid = $_GET['EmpID'];
        $result = $conn->query("SELECT * FROM Employee WHERE EmpID = $empid");
        $employee = $result->fetch_assoc();
    }

    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        $empid = $_POST['empid'];
        $name = $_POST['name'];
        $designation = $_POST['designation'];
        $salary = $_POST['salary'];
        $doj = $_POST['doj'];

        $stmt = $conn->prepare("UPDATE Employee SET Name = ?, Designation = ?, Salary = ?, DOJ = ? WHERE EmpID = ?");
        $stmt->bind_param("ssdsi", $name, $designation, $salary, $doj, $empid);

        if ($stmt->execute()) {
            echo "Employee details updated successfully.";
        } else {
            echo "Error updating employee: " . $conn->error;
        }
        $stmt->close();
        $conn->close();
    } else {
    ?>

    <h2>Update Employee Information</h2>
    <form method="POST" action="update_employee.php">
        <input type="hidden" name="empid" value="<?php echo $employee['EmpID']; ?>">

        <label for="name">Name:</label>
        <input type="text" name="name" value="<?php echo $employee['Name']; ?>" required><br>

        <label for="designation">Designation:</label>
        <input type="text" name="designation" value="<?php echo $employee['Designation']; ?>" required><br>

        <label for="salary">Salary:</label>
        <input type="number" name="salary" step="0.01" min="0" value="<?php echo $employee['Salary']; ?>" required><br>

        <label for="doj">Date of Joining:</label>
        <input type="date" name="doj" value="<?php echo $employee['DOJ']; ?>" required><br>

        <input type="submit" value="Update Employee">
    </form>

    <?php
    }
    ?>
</body>
</html>
