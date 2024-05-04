import { UserInterface } from "../../Interfaces/UserInterface"

import { Table } from "react-bootstrap"

export const ShUser : React.FC<UserInterface> = (usr:UserInterface) =>{



    return(

        <div>
           <div>

                <table className= "table"> 
                    <tbody>
                        <tr > 
                            <td> {usr.userId}</td>
                            <td> {usr.username}</td>
                            <td> {usr.firstname}</td>
                            <td> {usr.lastname}</td>
                            <td> {usr.role}</td>

                        </tr>
                    </tbody>
                </table>
            </div>


        </div>
    )
}