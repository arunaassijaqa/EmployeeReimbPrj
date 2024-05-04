import { useNavigate } from "react-router-dom"
import { state } from "../../GobalData/store"
import "./Reimbursement.css"
import axios from "axios"
import { useState } from "react"
import { ReimbInterface } from "../../Interfaces/ReimbInterface"
import { ShowReimb } from "./ShowReimb"

export const Reimbursement : React.FC = () =>{


    //we'll store state that consists of an Array of Reimbursement objects
    const [reimbList, setReimbList] = useState<ReimbInterface[]>([]) //start with empty a

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
    }
    if(input.target.value === "approved"){
        alert("Here We will show all approved reimbursements")
    }
    if(input.target.value === "denied"){
        alert("Here We will show all denied reimbursements")
    }
}


const createReimb = () =>{

    alert(" create new reimbersment")
    navigate("/creimb")

      
}

const showAllReimb =async () =>{

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
                
                <button className ="reimb-button2" onClick={createReimb} >Create Reimbursement </button>

                

                <button className ="reimb-button" onClick={() => navigate("/")}>Back</button>

                <select className="reimb-button2" name="selectReimb" onChange={getReimb}>
                    <option selected disabled value="selectmenu">Select Reimbursement Menu</option>
                    <option value="all">Show All Reimbursements</option>
                    <option value="pending">Show Pending Reimbursements </option>
                    <option value="approved">Show Approved Reimbursements</option>
                    <option value="denied">Show Denied Reimbursements</option>
                </select>
            </div>

            <div className="container-container">

                {/* using map(), for every pokemon that belongs to the logged in user... 
                Display one Pokemon component, and a button to delete it*/}
                {reimbList.map((reimb, index)  => 
                    <div>
                        <ShowReimb {...reimb}></ShowReimb>
                    
                    </div>
           )}

            {/* If you need to render multiple things in map(), they need to be in a <div> */}

        </div>
        </div>
    )
}