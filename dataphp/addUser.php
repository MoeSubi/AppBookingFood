<?php
include "connect.php";
$full_name = isset($_POST['full_name']) ? $_POST['full_name'] : '';
$phone_number = isset($_POST['phone_number']) ? $_POST['phone_number'] : '';
$email = isset($_POST['email']) ? $_POST['email'] : '';
$address = isset($_POST['address']) ? $_POST['address'] : '';
$password = isset($_POST['password']) ? $_POST['password'] : '';
$id_role = isset($_POST['id_role']) ? $_POST['id_role'] : '';
$pwHash=hash_hmac("sha512", $password, "hello");

$query = "INSERT INTO user (full_name, phone_number, email, address,password,
 id_role) VALUES ('$full_name', 
'$phone_number', '$email', '$address', '$pwHash','$id_role')"; 
$data = mysqli_query($conn, $query);

if ($data == true) {
        $arr = [
          'success' => true,
          'message' => "Add User Successful!"
        ];
}else {
  $arr = [
    'success' => false,
    'message' => "Add User Unsuccessful"
  ];
}

print_r(json_encode($arr));

?>

