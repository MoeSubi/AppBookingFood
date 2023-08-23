<?php  
include "connect.php";
$target_dir = "images/";  
$target_file_name = $target_dir .basename($_FILES["file"]["name"]);  

$query = "select max(id_detail) as id_detail from food_detail";
$data = mysqli_query($conn, $query);
$result = array();
while($row = mysqli_fetch_assoc($data)){
  $result[] = ($row);
} 



if ($result[0]['id_detail'] == null){
   $name = 1;
}else{
   $name = ++$result[0]['id_detail'];
}

$name = $name. ".jpg";  
$target_file_name = $target_dir .$name;

if (isset($_FILES["file"]))   
   {  
   if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name))  
      {  
         $arr = [
          'success' => true,
          'message' => "Thêm thành công",
          "name" => $name
        ];  
      }  
   else  
      {  
         $arr = [
          'success' => false,
          'message' => "Thêm không thành công"
        ];  
      }  
   }  
else  
   {  
      $arr = [
          'success' => false,
          'message' => "Lỗi"
        ]; 
   }  
   
   echo json_encode($arr);  
?>  