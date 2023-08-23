<?php
include "connect.php";
$category_name = isset($_POST['category_name']) ? $_POST['category_name'] : '';
$id_cate = isset($_POST['id_cate']) ? $_POST['id_cate'] : '';

$query = "UPDATE `category` SET `category_name`='$category_name' WHERE `id_cate` = ".$id_cate; 
$data = mysqli_query($conn, $query);
if ($data == true) {
        $arr = [
          'success' => true,
          'message' => "Edit Category Successful!"
        ];
}else {
  $arr = [
    'success' => false,
    'message' => "Edit Category Unsuccessful!"
  ];
}

print_r(json_encode($arr));

?>
