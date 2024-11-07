// App.jsx
import React from 'react';
import { Container, Nav, Navbar } from 'react-bootstrap';
import { NavLink, Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';
import CrossManagement from './components/CrossManagement';
import DogManagement from './components/DogManagement';
import EditHuntInfo from './components/EditHuntInfo';
import HuntCreationForm from './components/HuntCreationForm';
import HuntManagement from './components/HuntManagement';
import JudgeManagement from './components/JudgeManagement';
import SampleReportPage from './components/SampleReportPage';
import ScratchManagement from './components/ScratchManagement';
import SetStartTime from './components/SetStartTime';

function App() {
    return (
        <Router>
            <div className="App">
                <Navbar bg="dark" variant="dark" expand="lg">
                <Nav.Link 
                            as={NavLink} 
                            to="/" 
                            className="nav-link" 
                            style={({ isActive }) => ({ color: isActive ? 'white' : 'gray', fontSize: '1.3rem' , marginLeft: '15px', marginRight: '15px' })}
                        >
                            Hunt
                        </Nav.Link>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link as={NavLink} to="/dogs" className="nav-link">
                                Dogs
                            </Nav.Link>
                            <Nav.Link as={NavLink} to="/crosses" className="nav-link">
                                Crosses
                            </Nav.Link>
                            <Nav.Link as={NavLink} to="/judges" className="nav-link">
                                Judges
                            </Nav.Link>
                            <Nav.Link as={NavLink} to="/scratches" className="nav-link">
                                Scratches
                            </Nav.Link>
                            <Nav.Link as={NavLink} to="/report" className="nav-link">
                                Reports
                            </Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>

                <main className="mt-4">
                    <Container>
                        <Routes>
                            <Route path="/dogs" element={<DogManagement />} />
                            <Route path="/crosses" element={<CrossManagement />} />
                            <Route path="/judges" element={<JudgeManagement />} />
                            <Route path="/scratches" element={<ScratchManagement />} />
                            <Route path="/" element={<HuntManagement />} />
                            <Route path="/create-hunt" element={<HuntCreationForm />}/>
                            <Route path="/edit-hunt" element={<EditHuntInfo />} />
                            <Route path="/start-time" element={<SetStartTime />} />
                            <Route path="/report" element={<SampleReportPage />} />
                        </Routes>
                    </Container>
                </main>
            </div>
        </Router>
    );
}

export default App;

