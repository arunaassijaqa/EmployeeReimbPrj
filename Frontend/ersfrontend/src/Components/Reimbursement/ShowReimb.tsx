import { ReimbInterface } from "../../Interfaces/ReimbInterface"



export const ShowReimb : React.FC<ReimbInterface> = (reimb:ReimbInterface) =>{



    return(

        <div>
           <div>

                <table  className= "table" > 
                    <tbody>
                        <tr > 
                            <td> {reimb.reimbId}</td>
                            <td> {reimb.description}</td>
                            <td> {reimb.amount}</td>
                            <td> {reimb.status}</td>
                            <td> {reimb.userId}</td>

                        </tr>
                     </tbody>
                </table>
            </div>


        </div>
    )
}