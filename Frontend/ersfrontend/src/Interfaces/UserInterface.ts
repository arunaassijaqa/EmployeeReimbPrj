
//Just like a Java Model Class, our React Interfaces typically MODEL some data/data type we intend to use

export interface UserInterface{

    userId?:number,
    firstname?: string,
    lastname?: string,
    username: string,
    role?:string,
    password: string
}