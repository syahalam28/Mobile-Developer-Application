<?php 

include 'db_connect.php';

$response = array();

$nim = $_POST['nim'];
$nama = $_POST['nama'];

if(!$nim || !$nama){
	$response['success'] = 0;
	$response['msg'] = "Data tidak lengkap";
}else{
	$query = "INSERT INTO biodata(nim, nama) VALUES(?, ?)";

	if($stmt = $con->prepare($query)){
		$stmt->bind_param('ss', $nim, $nama);

		$stmt->execute();

		if($stmt){
			$response['success'] = 1;
			$response['msg'] = "Berhasil Tambah data";
		}else{
			$response['success'] = 0;
			$response['msg'] = "Gagal Tambah data";
		}
	}else{
		$response['success'] = 0;
		$response['msg'] = mysqli_error();
	}
}

echo json_encode($response);

?>