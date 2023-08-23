<?php
include "connect.php";
$user_id = $_POST['user_id'];
$query =  "DELETE FROM `user` WHERE `user_id` =" .$user_id;
$data = mysqli_query($conn, $query);


if ($data == true) {
        $arr = [
          'success' => true,
          'message' => "Remove Successfully!"
        ];
}else {
  $arr = [
    'success' => false,
    'message' => "Remove Unsuccessfully "
  ];
}

print_r(json_encode($arr));

?>

