import { useNavigate } from "react-router-dom"
import { state } from "../../GobalData/store"
import "./Reimbursement.css"
import axios from "axios"
import { useState } from "react"
import { ReimbInterface } from "../../Interfaces/ReimbInterface"
import { ShowReimb } from "./ShowReimb"
import { UserInterface } from "../../Interfaces/UserInterface"
import { ShUser } from "./ShowUser"
import { setEmitFlags } from "typescript"




export const Reimbursement : React.FC = () =>{


    //we'll store state that consists of an Array of Reimbursement objects
    const [reimbList, setReimbList] = useState<ReimbInterface[]>([]) //start with empty a

    const [userList, setUserList] = useState<UserInterface[]>([]) //start with empty a

    /*const[reimburse,setReimburse] = useState<ReimbInterface>( {
        reimbId : 0,
        description: "",
        amount : 0,
        status : "",
        userId : 0

         })*/

    
    

    const[flagEmplist,setFlagEmplist] = useState(false)
    const[flagRelist,setFlagRelist] = useState(false)

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

    //alert(" create new reimbersment")
    navigate("/creimb")

      
}

const showAllReimb =async () =>{

   

    //alert("Call Get axios and wait for response")
    //Send a POST request to the backend for create new user
    //NOTE: with credentials is what lets us save/send user session info
    const response = await axios.get(state.baseReimbUrl,
    {withCredentials:true})
    .then((response) => {

        setFlagEmplist(false)
        setReimbList(response.data)
        setFlagRelist(true) 
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

        setFlagEmplist(false)
        setReimbList(response.data)
          
        setFlagRelist(true) 
        console.log(response.data)
       

    })
    .catch((error) => {alert("show All Pending reimbursement request Failed!")}) //If login fails, tell the user that
    

}

const showAllEmployees =async () =>{

   //alert("showAllEmployees: lets call get method here")

    //Send a GET request to the backend for create new user
    //NOTE: with credentials is what lets us save/send user session info
    const response = await axios.get(state.baseUserUrl,
    {withCredentials:true})
    .then((response) => {

       setFlagRelist(false)
        setUserList(response.data)
        setFlagEmplist(true)
        
        console.log(response.data)
       

    })
    .catch((error) => {alert("show All users request Failed!")}) //If login fails, tell the user that

}

const apprReimb =async (reimb:ReimbInterface) =>{

    //alert("apprReimb: lets call post method here for approval")
    console.log(reimb)
    

    let url :string = state.baseReimbUrl + "/" + reimb?.reimbId

    console.log(url)
    
    reimb.status="approved"

    const response = await axios.put(url,reimb,
        {withCredentials:true})
        .then((response) => {
    
           
            setUserList(response.data)
            
            
            console.log(response.data)
           
    
        })
        .catch((error) => {alert("Approve Reimb request Failed!")}) //If login fails, tell the user that
    
 
     
 
 }

 const apprReimbdeny =async (reimb:ReimbInterface) =>{

    //alert("apprReimb: lets call post method here for approval")
    console.log(reimb)
    

    let url :string = state.baseReimbUrl + "/" + reimb?.reimbId

    console.log(url)
    
    reimb.status="denied"

    const response = await axios.put(url,reimb,
        {withCredentials:true})
        .then((response) => {
    
           
            setUserList(response.data)
            
            
            console.log(response.data)
           
    
        })
        .catch((error) => {alert("Denied Reimb request Failed!")}) //If login fails, tell the user that
    
 
     
 
 }

 //Delete user by id
 const userdelete = async(userId:number | undefined) => {

    let message:string = " Do you really want to delete?"
    
    //alert(userId)
    if (window.confirm(message)) {
        //If user say 'yes' to confirm
        console.log( ' is confirmed');
      
        //TODO: throw some error if userID is typeof undefined


        const response = await axios.delete(state.baseUserUrl +"/"+ userId, {withCredentials:true})
        .then((response) => {
        
            
            alert(response.data)
            
            
            console.log(response.data)
        

        })
        
        .catch(
            (error) => {alert("Denied Reimb request Failed!")}
        )

    } else {
    //If user say 'no' and cancelled the action
        console.log( ' is cancelled');
  }


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

            {flagRelist ? (

            <div className="reimb-container">
                 <table className = "table">                    
                    <th>
                        Reimbursement Details: {reimbList.length} records
                    </th>        
                </table>

                
                {reimbList.map((reimb, index)  => 
                    <div>
                        <ShowReimb {...reimb}></ShowReimb>
                        { ( (state.userSessionData.role === "manager") && (reimb.status==="pending" ) ) ? (
                            <button className ="reimb-approve" onClick={() => apprReimb(reimb)}>Approve </button>
                            
                            
                        ) : "" }

                        { ( (state.userSessionData.role === "manager") && (reimb.status==="pending" ) ) ? (
                             <button className ="reimb-approve" onClick={() => apprReimbdeny(reimb)}>Denied </button>
                           ) : "" }

                        

                    
                    </div>
                 )}

            </div>
            ) : ""  }

            {flagEmplist  ? (

            <div className="reimb-container">
                <table className = "table">                    
                    <th>
                        Employee Details: {userList.length} records
                    </th>        
                </table>
                <table className = "table">                    
                    
                    <td>
                        ID
                       </td>
                       <td>
                        Username
                       </td>
                       <td>
                        Firstname
                        </td>
                      
                        <td>
                        Lastname
                        </td>
                      
                        <td>
                        Role
                       </td>
                            
                </table>
                
                
                {userList.map((u, index)  => 
                <div>
                    <ShUser {...u}></ShUser>
                    { ( (state.userSessionData.role === "manager") ) ? (
                            <button className ="reimb-approve" onClick={() => userdelete(u.userId)}>Delete </button>
                            
                            
                        ) : "" }

                </div>
                )}
                

            </div>
            ) : "" }
        </div>
    )
}