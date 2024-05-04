import { UserInterface } from "../Interfaces/UserInterface";

/* This is a rudimentary implementation of a store, which is basically global data storage
any data that you want to store globally (visible to the entire app) can reside here
look into the context APi to see a more industry standard way of doing this (we'll talk later) */
export const state:any = {

    //we typically want to store user session info on the front end
    //for personalization as well as role-based security control
    userSessionData: {
        userId:0,
        username:"",
        firstname:"",
        lastname:"",
        role:""
        //role:"" <- This would be used to determine if a user can do certain things
    } as UserInterface,

    

    //Think about your requirements when storing state globally
    //you only NEED to globally store data you intend to use in multiple components
    //but you could optimize you code by using global storage to reduce calls to your API

    //we could also store things like base URLs (which I won't use)
    baseUrl:"http://localhost:8080",
    baseUserUrl:"http://localhost:8080/users",
    baseLoginUrl:"http://localhost:8080/users/login",
    baseReimbUrl:"http://localhost:8080/reimbursements"

    //TODO: store our incoming JWT 

}

//Side note: typically all global user data will get populated upon successful login
//Other side note: There's no built in getter/setter mechanisms, which would be nice for encap.

//another one: For a solution like this, we should probably use local/session storage, for those getters/setters as as data that persists on page reload 
//but I'm just trying to set the stage for context API here