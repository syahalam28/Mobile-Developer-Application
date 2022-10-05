<?php
 
    include 'db_connect.php';
    $response = array();
     
    if(isset($_POST['id'])){
        
        $id = $_POST['id'];        
        
        $query = "DELETE FROM biodata WHERE id=?";
      
        //Prepare the query
        if($stmt = $con->prepare($query)){
            //Bind parameters
            $stmt->bind_param("i",$id);
            //Exceting MySQL statement
            $stmt->execute();
            
            if($stmt){
                $response["success"] = 1;           
                $response["message"] = "Sukses Hapus";          
                
            }else{
                
                $response["success"] = 0;
                $response["message"] = "Gagal Hapus";
            }                   
        }else{
            
            $response["success"] = 0;
            $response["message"] = mysqli_error($con);
        }
     
    }else{
        //Mandatory parameters are missing
        $response["success"] = 0;
        $response["message"] = "Missing id";
    }
    //Displaying JSON response
    echo json_encode($response);
?>