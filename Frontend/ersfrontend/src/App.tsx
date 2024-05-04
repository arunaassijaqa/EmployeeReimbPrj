import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Login } from './Components/Login/Login';
import { Register } from './Components/Login/Register';
import { Reimbursement } from './Components/Reimbursement/Reimbursement';
import { NewReimb } from './Components/Reimbursement/NewReimb';



function App() {
  return (
    <div className="App">
      <BrowserRouter>
          <Routes>
              <Route path="" element={<Login/>}/>
              <Route path="/register" element={<Register/>}/>
              <Route path="/reimbursements" element={<Reimbursement/>}/>
              
              <Route path="/creimb" element={<NewReimb/>}/>
              e
          </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;



