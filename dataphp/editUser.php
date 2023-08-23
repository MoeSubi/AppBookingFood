<?php
include "connect.php";
$full_name = isset($_POST['full_name']) ? $_POST['full_name'] : '';
$phone_number = isset($_POST['phone_number']) ? $_POST['phone_number'] : '';
$email = isset($_POST['email']) ? $_POST['email'] : '';
$address = isset($_POST['address']) ? $_POST['address'] : '';
$password = isset($_POST['password']) ? $_POST['password'] : '';
$id_role = isset($_POST['id_role']) ? $_POST['id_role'] : '';
$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : '';
$pwHash=hash_hmac("sha512", $password, "hello");

$query = "UPDATE `user` SET `full_name`='$full_name',`phone_number`='$phone_number',
`email`='$email',`address`='$address',`password`='$pwHash' ,`id_role`='$id_role' WHERE `user_id` = ".$user_id; 
$data = mysqli_query($conn, $query);
if ($data == true) {
        $arr = [
          'success' => true,
          'message' => "Edit User Successful!"
        ];
}else {
  $arr = [
    'success' => false,
    'message' => "Edit User Unsuccessful!"
  ];
}

print_r(json_encode($arr));

?>

