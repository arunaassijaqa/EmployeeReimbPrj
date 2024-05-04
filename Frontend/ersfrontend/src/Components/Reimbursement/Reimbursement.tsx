import { useNavigate } from "react-router-dom"
import { state } from "../../GobalData/store"
import "./Reimbursement.css"

export const Reimbursement : React.FC = () =>{

//useNavigate to navigate between components
const navigate = useNavigate()


const getAllReimb = () => 
{

        alert("Here We will show all reimbursements")
}


const createReimb = () =>{

    alert(" create new reimbersment")
    navigate("/creimb")

    

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

                <select className="reimb-button2" name="selectReimb" onChange={getAllReimb}>
                    <option selected disabled value="selectmenu">Select Reimbursement Menu</option>
                    <option value="all">Show All Reimbursements</option>
                    <option value="pending">Show Pending Reimbursements </option>
                    <option value="approved">Show Approved Reimbursements</option>
                    <option value="denied">Show Denied Reimbursements</option>
                </select>
            </div>
        </div>
    )
}