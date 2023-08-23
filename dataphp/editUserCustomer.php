<?php
include "connect.php";
$full_name = isset($_POST['full_name']) ? $_POST['full_name'] : '';
$phone_number = isset($_POST['phone_number']) ? $_POST['phone_number'] : '';
$email = isset($_POST['email']) ? $_POST['email'] : '';
$address = isset($_POST['address']) ? $_POST['address'] : '';

$query = "UPDATE `user` SET `full_name`='$full_name',`phone_number`='$phone_number',
`email`='$email',`address`='$address'"; 

$data = mysqli_query($conn, $query);
if ($data == true) {
        $arr = [
          'success' => true,
          'message' => "Edit Successful!"
        ];
}else {
  $arr = [
    'success' => false,
    'message' => "Edit Unsuccessful!"
  ];
}

print_r(json_encode($arr));

?>
