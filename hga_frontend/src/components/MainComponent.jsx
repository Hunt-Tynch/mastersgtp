import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const MainComponent = () => {

    const [hunt, setHunt] = useState( {id: null, name: null, date: null, interval: null} )
    const dummyHunt = {
        name: "Test Hunt",
        date: "10-10-2024",
        interval: 10
    }

    useEffect( () => {
        setHunt(dummyHunt)
    }, [] )

    const navigator = useNavigate()

    function createNewHunt() {
        navigator('/create-hunt')
    }

    return (
        <div className="container d-flex flex-column mt-5 align-items-center" style={{ height: '100vh' }}>
            <div className="text-center mt-5">
                <h1 className="large-text">Master's</h1>
            </div>
            <div className="text-center mt-3"> {/* Margin top for spacing */}
                <button className="btn btn-secondary" onClick={ createNewHunt }>New Hunt</button>
            </div>
            <div className="text-center mt-5" style={ {backgroundColor: 'lightgray', border: '2px solid black', paddingRight: '50px', paddingLeft: '50px', borderRadius: '5px', borderWidth: '3px'} }> {/* Margin top for spacing */}
                <h2 className="large-text">Current Hunt</h2>
                <table className="table table-striped table-bordered" style = {{height: '5vh'}}>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Date</th>
						<th>Interval</th>
                    </tr>
                </thead>
                <tbody>
                    <tr key={hunt.id}>
                        <td className="medium-text" style={{width: '300px'}}>{hunt.name}</td>
                        <td className="medium-text" style={{width: '300px'}}>{hunt.date}</td>
                        <td className="medium-text" style={{width: '300px'}}>{hunt.interval}</td>
                    </tr>
                </tbody>
                </table>
            </div>
        </div>
    );
}

export default MainComponent;
