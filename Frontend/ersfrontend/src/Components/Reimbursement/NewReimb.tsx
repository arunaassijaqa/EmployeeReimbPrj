import { useState } from "react"
import { ReimbInterface } from "../../Interfaces/ReimbInterface"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import { state } from "../../GobalData/store"

export const NewReimb : React.FC = () =>{

    const[ reimb,setReimb] = useState<ReimbInterface>({
        reimbId : 0,
        description: "",
        amount : 0,
        status : "",
        userId : 0
        })

    //useNavigate to navigate between components
    const navigate = useNavigate()

    const [returndata,setReturndata ] = useState("")

    //function to store input box values
    const storeValues = (input:any) => {

        //if the input that has changed is the "username" input, change the value of username in the reimb state object

        /*if(input.target.name === "reimbId"){
            setReimb((reimb) => ({...reimb, reimbId:input.target.value}))
        } */
        if(input.target.name === "description"){
            setReimb((reimb) => ({...reimb, description:input.target.value}))

        }  
        if(input.target.name === "amount"){
            setReimb((reimb) => ({...reimb, amount:input.target.value}))
        } 
       
        console.log("Inside store value of newreimb")

        console.log(reimb)

    }

     //This function will d send a POST to our java server for creating reimb in database 
     const createReimb = async () => {

        //TODO: We could (should) validate reimb input here as well as backend 
        //console.log("Inside createReimb function ")

        //console.log(reimb)

        if( reimb.amount <=0 )
            {
                //console.log("Inside createReimb function :validation failed")


                alert("Please enter  amount greater than zero ")
                return
            }

        console.log("Inside createReimb function: send post request here ")


        //Send a POST request to the backend for create new reimb
        //NOTE: with credentials is what lets us save/send reimb session info
        const response = await axios.post(state.baseReimbUrl,
        reimb,
        {withCredentials:true})
        .then((response) => {

            //if the login was successful, log the reimb in and store their info in global state
            //state.userSessionData = response.data
            
            console.log(response.data)

            setReturndata(response.data)
            

            
        })
        .catch((error) => {alert("Create new reimbursement Failed!")}) //If login fails, tell the reimb that

    }

    return(

        <div className ="login">
            <div className ="text-container">
            <h4>Enter New Reimbursement Details</h4>

            <br></br>
            <br></br>
            
            <div className ="input-container">                
                <input type ="text" placeholder="description(optional)" name="description" onChange={storeValues}></input>
            </div>
            <br></br>
            <div className ="input-container">
                <input type ="number" placeholder="amount(Required)" name="amount" onChange={storeValues}></input>
            </div>

            
            <div>
                
            {returndata   ? <p> {returndata}</p> : '' }
            </div>
            
            <br></br>
            <button className ="login-button" onClick= {createReimb}>Submit</button>
            <button className ="login-button" onClick={() => navigate("/reimbursements")}>Back</button>

           
        
            </div>
        </div>
    )

}