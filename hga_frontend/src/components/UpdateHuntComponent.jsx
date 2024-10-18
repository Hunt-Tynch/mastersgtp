import React, { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import { listHunt, putHunt } from '../services/HuntService';

const UpdateHuntComponent = () => {
    const [hunt, setHunt] = useState({name: '', date: '', timeInterval: '' });
    const [error, setError] = useState('');
    const [success, setSuccess] =useState('');
    const navigator = useNavigate();

    useEffect( () => {
        listHunt().then(ret => {
            setHunt(ret.data)
        }).catch(err => {
            console.log(err)
        })
    } , [])

    function updateHunt() {
        // Validate input fields
        if (!hunt.name || !hunt.date || hunt.timeInterval <= 0) {
            setError('All fields are required and time interval must be greater than 0.');
            setSuccess('')
            return;
        }

        // Reset the error and proceed to create the hunt
        setError('');
        setSuccess('Hunt updated.')
        putHunt(hunt).then( ret => {setHunt(ret.data)}).catch( err => {
            console.log(err)
        });
    }

    function backToMain() {
        navigator('/')
    }

    return (
        <div className="container d-flex flex-column mt-5 align-items-center" style={{ height: '100vh' }}>
            <div className="text-center mt-3">
                <h1 className="large-text">Master's</h1>
            </div>
            <div className="text-center mt-4" style={{ backgroundColor: 'lightgray', border: '2px solid black', paddingRight: '50px', paddingLeft: '50px', borderRadius: '5px', borderWidth: '3px', paddingBottom: '20px' }}>
                <h2 className="large-text">New Hunt</h2>
                {error && <div className="alert alert-danger">{error}</div>}
                {success && <div className="alert alert-success">{success}</div>}
                <table className="table table-striped table-bordered" style={{ height: '5vh' }}>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Date</th>
                            <th>Interval</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td className="medium-text" style={{ width: '300px' }}>
                                <input
                                    className="text"
                                    value={hunt.name}
                                    onChange={e => setHunt(prev => ({ ...prev, name: e.target.value }))}
                                />
                            </td>
                            <td className="medium-text" style={{ width: '300px' }}>
                                <input
                                    className="text"
                                    value={hunt.date}
                                    onChange={e => setHunt(prev => ({ ...prev, date: e.target.value }))}
                                />
                            </td>
                            <td className="medium-text" style={{ width: '300px' }}>
                                <input
                                    className="text"
                                    value={hunt.timeInterval}
                                    onChange={e => setHunt(prev => ({ ...prev, timeInterval: Number(e.target.value) }))}
                                />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div className="text-center mt-3">
                    <button className="btn btn-primary" style={{ width: '150px' }} onClick={updateHunt}>Update</button>
                </div>
                <div className="text-center mt-3">
                    <button className="btn btn-primary" style={{ width: '150px' }} onClick={backToMain}>Main Menu</button>
                </div>
            </div>
        </div>
    );
}

export default UpdateHuntComponent