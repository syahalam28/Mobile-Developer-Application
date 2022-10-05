<?php
 
    include 'db_connect.php';

    $movieArray = array();
    $response = array();

    $query = ("SELECT * FROM biodata") ;
    // print_r($query);
    // exit();

    $result = array();
        //Prepare the query
    if($stmt = $con->prepare($query)){
        $stmt->execute();            
        $stmt->bind_result($id,$nim,$nama);
        
        while($stmt->fetch()){
            $movieArray = array();                    
            $movieArray["id"] = $id;
            $movieArray["nim"] = $nim;                
            $movieArray["nama"] = $nama;
            $result[]=$movieArray;
            
        }
        $stmt->close();
        $response["success"] = 1;
        $response["data"] = $result;
        
     
    }else{
        $response["success"] = 0;
        $response["message"] = mysqli_error($con);
            
        
    }

    //Display JSON response
    echo json_encode($response);
?>