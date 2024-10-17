import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import HeaderComponent from './components/HeaderComponent';
import MainComponent from './components/MainComponent';

function App() {
  return (
    <>
    <BrowserRouter>
      <div className="d-flex min-vh-100 flex-column justify-content-between">
        <div>
          <HeaderComponent />
          <Routes>
            <Route path='/' element = { <MainComponent /> }></Route>
          </Routes>
        </div>
      </div>
    </BrowserRouter>
    </>
  )
}

export default App;
