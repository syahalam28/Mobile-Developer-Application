<?php
 
    include 'db_connect.php';
    $response = array();
     
    //Check for mandatory parameters
    if(isset($_POST['id'])){

        $id = $_POST['id'];    
        $nim = $_POST['nim'];
        $nama = $_POST['nama'];
                
        //Query to insert a movie
        $query = "UPDATE biodata SET nim =?, nama=? WHERE id=?";
        //Prepare the query
        if($stmt = $con->prepare($query)){
            //Bind parameters
            $stmt->bind_param("ssi",$nim,$nama,$id);
            //Exceting MySQL statement
            $stmt->execute();
            //Check if data got inserted
            if($stmt){
                $response["success"] = 1;           
                $response["message"] = "Sukses Update";          
                
            }else{
                
                $response["success"] = 0;
                $response["message"] = "Gagal Update";
            }                   
        }else{
            //Some error while inserting
            $response["success"] = 0;
            $response["message"] = mysqli_error($con);
        }
     
    }else{
        //Mandatory parameters are missing
        $response["success"] = 0;
        $response["message"] = "tidak ada parameter";
    }
    //Displaying JSON response
    echo json_encode($response);
?>