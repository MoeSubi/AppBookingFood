<?php
include "connect.php";
$category_name = isset($_POST['category_name']) ? $_POST['category_name'] : '';



$query = "INSERT INTO `category` (`category_name`) VALUES ('$category_name')"; 
$data = mysqli_query($conn, $query);

if ($data == true) {
        $arr = [
          'success' => true,
          'message' => "Add Category Successful!"
        ];
}else {
  $arr = [
    'success' => false,
    'message' => "Add Category Unsuccessful!"
  ];
}

print_r(json_encode($arr));

?>



