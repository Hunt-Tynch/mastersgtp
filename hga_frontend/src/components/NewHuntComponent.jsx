import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { postHunt } from '../services/HuntService';

const NewHuntComponent = () => {
    const [hunt, setHunt] = useState({ name: '', date: '', timeInterval: '' });
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigator = useNavigate();

    function createHunt() {
        // Validate input fields
        if (!hunt.name || !hunt.date || hunt.timeInterval <= 0) {
            setError('All fields are required and time interval must be greater than 0.');
            setSuccess('');
            return;
        }

        // Reset the error and proceed to create the hunt
        setError('');
        setSuccess('New Hunt Created.');
        postHunt(hunt).then(ret => {
            setHunt(ret.data);
        }).catch(err => {
            console.log(err);
        });
    }

    function backToMain() {
        navigator('/');
    }

    return (
        <div className="container d-flex flex-column mt-5 align-items-center" style={{ height: '100vh' }}>
            <div className="text-center mt-3">
                <h1 className="large-text">Master's</h1>
            </div>
            <div className="text-center mt-4" style={{ backgroundColor: '#c0c0c0', border: '3px solid #333', padding: '30px', borderRadius: '8px', width: '75vw' }}>
                <h2 className="large-text">New Hunt</h2>
                {error && <div className="alert alert-danger">{error}</div>}
                {success && <div className="alert alert-success">{success}</div>}
                <table className="table table-striped table-bordered" style={{ height: '5vh', backgroundColor: '#343a40', color: 'white' }}>
                    <thead>
                        <tr style={{ backgroundColor: '#007bff', color: 'white' }}>
                            <th>Name</th>
                            <th>Date</th>
                            <th>Interval</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td className="medium-text" style={{ width: '300px' }}>
                                <input
                                    className="form-control"
                                    value={hunt.name}
                                    onChange={e => setHunt(prev => ({ ...prev, name: e.target.value }))}
                                />
                            </td>
                            <td className="medium-text" style={{ width: '300px' }}>
                                <input
                                    className="form-control"
                                    value={hunt.date}
                                    onChange={e => setHunt(prev => ({ ...prev, date: e.target.value }))}
                                />
                            </td>
                            <td className="medium-text" style={{ width: '300px' }}>
                                <input
                                    className="form-control"
                                    value={hunt.timeInterval}
                                    onChange={e => setHunt(prev => ({ ...prev, timeInterval: Number(e.target.value) }))}
                                />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div className="text-center mt-3">
                    <button className="btn btn-secondary" style={{ width: '150px' }} onClick={createHunt}>Create</button>
                </div>
                <div className="text-center mt-3">
                    <button className="btn btn-secondary" style={{ width: '150px' }} onClick={backToMain}>Main Menu</button>
                </div>
            </div>
        </div>
    );
}

export default NewHuntComponent;
