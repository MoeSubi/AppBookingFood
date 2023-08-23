<?php
include "connect.php";

$email = isset($_POST['email']) ? $_POST['email'] : '';
$password = isset($_POST['password']) ? $_POST['password'] : '';
$pwHash=hash_hmac("sha512", $password, "hello");
// Prepare the query using prepared statements
$query = 'SELECT * FROM `user` WHERE `email` ="'.$email.'" AND `password` ="'.$pwHash.'"';
$data = mysqli_query($conn, $query);

if (!$data) {
    echo "Lỗi truy vấn: " . mysqli_error($conn);
    // Hoặc ghi log lỗi, xử lý lỗi phù hợp...
} else {
    $result = array();
    while ($row = mysqli_fetch_assoc($data)) {
        $result[] = $row;
    }

    if (!empty($result)) {
        $arr = [
            'success' => true,
            'message' => "Login Successful!!!",
            'result' => $result
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => "Login Unsuccessful!!!",
            'result' => $result
        ];
    }

    print_r(json_encode($arr));
}
?>
