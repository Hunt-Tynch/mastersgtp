import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { listHunt } from '../services/HuntService';

const MainComponent = () => {
    const [hunt, setHunt] = useState({ id: null, name: null, date: null, timeInterval: null });

    useEffect(() => {
        listHunt()
            .then((ret) => { setHunt(ret.data); })
            .catch(err => { console.log(err); });
    }, []);

    const navigator = useNavigate();

    function createNewHunt() {
        navigator('/create-hunt');
    }

    function updateHunt() {
        navigator('/update-hunt');
    }

    return (
        <div className="container d-flex flex-column mt-5 align-items-center" style={{ height: '100vh'}}>
            <div className="text-center mt-3">
                <h1 className="large-text">Master's</h1>
            </div>
            <div className="text-center mt-4" style={{ backgroundColor: '#c0c0c0', border: '3px solid #333', padding: '30px', borderRadius: '8px', width: '75vw' }}>
                <h2 className="large-text">Current Hunt</h2>
                <table className="table table-striped table-bordered table-dark" style={{ height: '6vh' }}>
                    <thead>
                        <tr style={{ backgroundColor: '#007bff', color: 'white' }}>
                            <th>Name</th>
                            <th>Date</th>
                            <th>Interval</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr key={hunt.id}>
                            <td className="medium-text" style={{ width: '300px' }}>{hunt.name}</td>
                            <td className="medium-text" style={{ width: '300px' }}>{hunt.date}</td>
                            <td className="medium-text" style={{ width: '300px' }}>{hunt.timeInterval}</td>
                        </tr>
                    </tbody>
                </table>
                <div className="text-center mt-3">
                    <button className="btn btn-secondary" style={{ width: '150px' }} onClick={createNewHunt}>New Hunt</button>
                </div>
                <div className="text-center mt-3">
                    <button className="btn btn-secondary" style={{ width: '150px' }} onClick={updateHunt}>Update Hunt</button>
                </div>
            </div>
            <div className="container mt-5" style={{ background: '#c0c0c0', width: '75vw', border: '3px solid #333', borderRadius: '8px' }}>
                <h2 className="large-text text-center">Reports</h2>
                <div className="container text-center">
                    {Array.from({ length: 4 }).map((_, index) => (
                        <div className='row-col-1 mb-3' key={index}>
                            <button className='btn btn-secondary mx-4 col-2'>Button {index * 3 + 1}</button>
                            <button className='btn btn-secondary mx-4 col-2'>Button {index * 3 + 2}</button>
                            <button className='btn btn-secondary mx-4 col-2'>Button {index * 3 + 3}</button>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default MainComponent;
