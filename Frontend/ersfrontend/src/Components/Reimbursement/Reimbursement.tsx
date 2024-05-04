import { useNavigate } from "react-router-dom"
import { state } from "../../GobalData/store"
import "./Reimbursement.css"
import axios from "axios"
import { useState } from "react"
import { ReimbInterface } from "../../Interfaces/ReimbInterface"
import { ShowReimb } from "./ShowReimb"
import { UserInterface } from "../../Interfaces/UserInterface"
import { ShUser } from "./ShowUser"




export const Reimbursement : React.FC = () =>{


    //we'll store state that consists of an Array of Reimbursement objects
    const [reimbList, setReimbList] = useState<ReimbInterface[]>([]) //start with empty a

    const [userList, setUserList] = useState<UserInterface[]>([]) //start with empty a

//useNavigate to navigate between components
const navigate = useNavigate()


const getReimb = (input:any) => 
{
   

    if(input.target.value === "all"){
        alert("Here We will show all reimbursements")
        showAllReimb()
    }
    if(input.target.value === "pending"){
        alert("Here We will show all pending reimbursements")

        showOtherStatusReimb(input.target.value)
    }
    if(input.target.value === "approved"){
        alert("Here We will show all approved reimbursements")
        showOtherStatusReimb(input.target.value)
    }
    if(input.target.value === "denied"){
        alert("Here We will show all denied reimbursements")
        showOtherStatusReimb(input.target.value)
    }
}


const createReimb = () =>{

    alert(" create new reimbersment")
    navigate("/creimb")

      
}

const showAllReimb =async () =>{

   

    alert("Call Get axios and wait for response")
    //Send a POST request to the backend for create new user
    //NOTE: with credentials is what lets us save/send user session info
    const response = await axios.get(state.baseReimbUrl,
    {withCredentials:true})
    .then((response) => {

       
        setReimbList(response.data)
        
        console.log(response.data)
       

    })
    .catch((error) => {alert("show All reimbursement request Failed!")}) //If login fails, tell the user that

}
const showOtherStatusReimb =async (status:string) =>{

    console.log(" inside showOtherStatusReimb")
    console.log(status)
    

    //Send a POST request to the backend for create new user
    //NOTE: with credentials is what lets us save/send user session info
    const response = await axios.get(state.baseReimbUrl+"/"+status,
    {withCredentials:true})
    .then((response) => {

       
        setReimbList(response.data)
        
        console.log(response.data)
       

    })
    .catch((error) => {alert("show All Pending reimbursement request Failed!")}) //If login fails, tell the user that
    

}

const showAllEmployees =async () =>{

   alert("showAllEmployees: lets call get method here")

    //Send a POST request to the backend for create new user
    //NOTE: with credentials is what lets us save/send user session info
    const response = await axios.get(state.baseUserUrl,
    {withCredentials:true})
    .then((response) => {

       
        setUserList(response.data)
        
        console.log(response.data)
       

    })
    .catch((error) => {alert("show All users request Failed!")}) //If login fails, tell the user that

}




    return(

        <div>
            <div>
            <p> Welcome {state.userSessionData.firstname}  {state.userSessionData.lastname} 
                <br />
                Role: {state.userSessionData.role} 
                <br />
                 Username: {state.userSessionData.username} </p>
            </div>
            <div className = "text-container">
                
                <button className ="reimb-createbutton" onClick={createReimb} >Create Reimbursement </button>

                

                <button className ="reimb-backbutton" onClick={() => navigate("/")}>Back</button>
                <button className ="reimb-allempbutton" onClick={showAllEmployees}>Show All Employees </button>

                <select className="reimb-selectreimb" name="selectReimb" onChange={getReimb}>
                    <option selected disabled value="selectmenu">Select Reimbursement Menu</option>
                    <option value="all">Show All Reimbursements</option>
                    <option value="pending">Show Pending Reimbursements </option>
                    <option value="approved">Show Approved Reimbursements</option>
                    <option value="denied">Show Denied Reimbursements</option>
                </select>
            </div>

            <div className="reimb-container">

                
                {reimbList.map((reimb, index)  => 
                    <div>
                        <ShowReimb {...reimb}></ShowReimb>
                    
                    </div>
                 )}


            </div>

            <div className="reimb-container">

                
                {userList.map((u, index)  => 
                <div>
                    <ShUser {...u}></ShUser>

                </div>
                )}


            </div>
        </div>
    )
}