<?php
include "connect.php";
$id_cate = $_POST['id_cate'];
$query =  'DELETE FROM `category` WHERE `id_cate` ='.$id_cate;
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



