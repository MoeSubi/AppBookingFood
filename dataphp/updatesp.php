<?php
include "connect.php";
$food_name = isset($_POST['food_name']) ? $_POST['food_name'] : '';
$price = isset($_POST['price']) ? $_POST['price'] : '';
$image_food = isset($_POST['image_food']) ? $_POST['image_food'] : '';
$description = isset($_POST['description']) ? $_POST['description'] : '';
$id_cate = isset($_POST['id_cate']) ? $_POST['id_cate'] : '';
$id_detail= isset($_POST['id_detail']) ? $_POST['id_detail'] : '';


$query = "UPDATE `food_detail` SET `food_name`='$food_name',`price`='$price',`image_food`='$image_food',`description`='$description',
`id_cate`='$id_cate' WHERE `id_detail` = ".$id_detail; 
$data = mysqli_query($conn, $query);

if ($data == true) {
        $arr = [
          'success' => true,
          'message' => "Sửa thành công"
        ];
}else {
  $arr = [
    'success' => false,
    'message' => "Sửa không thành công"
  ];
}

print_r(json_encode($arr));

?>

