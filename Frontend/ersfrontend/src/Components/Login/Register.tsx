import { useState } from "react"
import { UserInterface } from "../../Interfaces/UserInterface"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import { state } from "../../GobalData/store"

export const Register : React.FC =() => {

    const[ user,setUser] = useState<UserInterface>({
        firstname:"",
        lastname:"",
        username:"",
        password :"",
        role:""
    })

    //useNavigate to navigate between components
    const navigate = useNavigate()

    //function to store input box values
    const storeValues = (input:any) => {

        //if the input that has changed is the "username" input, change the value of username in the user state object

        if(input.target.name === "firstname"){
            setUser((user) => ({...user, firstname:input.target.value}))
        } 
        if(input.target.name === "lastname"){
            setUser((user) => ({...user, lastname:input.target.value}))

        }  
        if(input.target.name === "username"){
            setUser((user) => ({...user, username:input.target.value}))
        } 
        if(input.target.name === "password"){
            setUser((user) => ({...user, password:input.target.value}))
        } 
        if(input.target.name === "selectrole"){
            setUser((user) => ({...user, role:input.target.value}))
        } 
        console.log("Inside store value")

        console.log(user)

    }

     //This function will d send a POST to our java server for creating user in database 
     const createuser = async () => {

        //TODO: We could (should) validate user input here as well as backend 
        //console.log("Inside createuser function ")

        //console.log(user)

        if(user.firstname == "" || user.lastname == "" || user.password == "" || user.role == "" || user.username == "" )
            {
                //console.log("Inside createuser function :validation failed")


                alert("Please enter all fields before proceeding")
                return
            }

            console.log("Inside createuser function: send post request here ")


        //Send a POST request to the backend for create new user
        //NOTE: with credentials is what lets us save/send user session info
        const response = await axios.post(state.baseUserUrl,
        user,
        {withCredentials:true})
        .then((response) => {

            //if the login was successful, log the user in and store their info in global state
            state.userSessionData = response.data
            
            console.log(state.userSessionData)

            alert("created , " + state.userSessionData.username)

            //use our useNavigate hook to switch views to the Catch Pokemon Component
            navigate("/")

        })
        .catch((error) => {alert("Registered new user Failed!")}) //If login fails, tell the user that

    }

    return(

        <div>
            <h1>Enter Employee Information</h1>

            <div className ="input-container">
                <input type ="text" placeholder="firstname" name="firstname" onChange={storeValues}></input>
            </div>

            <div className ="input-container">
                <input type ="text" placeholder="lastname" name="lastname" onChange={storeValues}></input>
            </div>

            <div className ="input-container">
                <input type ="text" placeholder="username" name="username" onChange={storeValues}></input>
            </div>

            <div className ="input-container">
                <input type ="password" placeholder="password" name="password" onChange={storeValues}></input>
            </div>


            <div className ="input-container">
                <select name="selectrole" id="role" onChange={storeValues}>
                    <option selected disabled value="selectrole">Select Role</option>
                    <option value="employee">employee</option>
                    <option value="manager">manager</option>
                </select>

            </div>

            <button className ="login-button" onClick= {createuser}>Register</button>
            <button className ="login-button" onClick={() => navigate("/")}>Back</button>
        

        </div>
    )

}