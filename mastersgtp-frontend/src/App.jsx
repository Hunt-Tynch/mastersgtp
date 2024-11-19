import React from 'react';
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
            <div className="App bg-gray-100 min-h-screen">
                {/* Navbar */}
                <div className="navbar bg-neutral text-neutral-content">
                    <div className="navbar-start">
                        <ul className="menu menu-horizontal space-x-4">
                            <li>
                                <NavLink 
                                    to="/" 
                                    className={({ isActive }) => 
                                        `btn btn-ghost normal-case text-md ${isActive ? 'text-white' : 'text-gray-400'}`
                                    }
                                >
                                    Hunt
                                </NavLink>
                            </li>
                            <li>
                                <NavLink 
                                    to="/dogs" 
                                    className={({ isActive }) => 
                                        `btn btn-ghost normal-case text-md ${isActive ? 'text-white' : 'text-gray-400'}`
                                    }
                                >
                                    Dogs
                                </NavLink>
                            </li>
                            <li>
                                <NavLink 
                                    to="/crosses" 
                                    className={({ isActive }) => 
                                        `btn btn-ghost normal-case text-md ${isActive ? 'text-white' : 'text-gray-400'}`
                                    }
                                >
                                    Crosses
                                </NavLink>
                            </li>
                            <li>
                                <NavLink 
                                    to="/judges" 
                                    className={({ isActive }) => 
                                        `btn btn-ghost normal-case text-md ${isActive ? 'text-white' : 'text-gray-400'}`
                                    }
                                >
                                    Judges
                                </NavLink>
                            </li>
                            <li>
                                <NavLink 
                                    to="/scratches" 
                                    className={({ isActive }) => 
                                        `btn btn-ghost normal-case text-md ${isActive ? 'text-white' : 'text-gray-400'}`
                                    }
                                >
                                    Scratches
                                </NavLink>
                            </li>
                            <li>
                                <NavLink 
                                    to="/report" 
                                    className={({ isActive }) => 
                                        `btn btn-ghost normal-case text-md ${isActive ? 'text-white' : 'text-gray-400'}`
                                    }
                                >
                                    Reports
                                </NavLink>
                            </li>
                        </ul>
                    </div>
                </div>

                {/* Main Content */}
                <main className="p-4">
                    <div className="container mx-auto bg-white p-6 rounded-lg shadow">
                        <Routes>
                            <Route path="/dogs" element={<DogManagement />} />
                            <Route path="/crosses" element={<CrossManagement />} />
                            <Route path="/judges" element={<JudgeManagement />} />
                            <Route path="/scratches" element={<ScratchManagement />} />
                            <Route path="/" element={<HuntManagement />} />
                            <Route path="/create-hunt" element={<HuntCreationForm />} />
                            <Route path="/edit-hunt" element={<EditHuntInfo />} />
                            <Route path="/start-time" element={<SetStartTime />} />
                            <Route path="/report" element={<SampleReportPage />} />
                        </Routes>
                    </div>
                </main>
            </div>
        </Router>
    );
}

export default App;
