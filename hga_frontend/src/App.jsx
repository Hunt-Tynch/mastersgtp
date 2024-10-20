import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import DogAndKennelComponent from './components/DogAndKennelComponent';
import HeaderComponent from './components/HeaderComponent';
import JudgeComponent from './components/JudgeComponent';
import MainComponent from './components/MainComponent';
import NewHuntComponent from './components/NewHuntComponent';
import UpdateHuntComponent from './components/UpdateHuntComponent';


function App() {
  return (
    <>
    <BrowserRouter>
      <div className="d-flex min-vh-100 flex-column justify-content-between" style={{ background: '#999999'}}>
        <div>
          <HeaderComponent />
          <Routes>
            <Route path='/' element = { <MainComponent /> }></Route>
            <Route path='/create-hunt' element = {<NewHuntComponent/>}></Route>
            <Route path='/update-hunt' element={<UpdateHuntComponent />}></Route>
            <Route path='/dogs-and-kennels' element={<DogAndKennelComponent />}></Route>
            <Route path='/judges' element={<JudgeComponent/>}></Route>
          </Routes>
        </div>
      </div>
    </BrowserRouter>
    </>
  )
}

export default App;
