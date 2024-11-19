import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getHunt } from '../service/HuntService';

const HuntManagement = () => {
    const [hunt, setHunt] = useState({
        name: '',
        date: '',
        timeInterval: '',
    });
    const [startTimes, setStartTimes] = useState(['', '', '', '']);

    useEffect(() => {
        getHunt()
            .then(ret => {
                if (ret.data) {
                    const formattedTimes = ret.data.startTimes.map(total => {
                        const hours = Math.floor(total / 60);
                        const minutes = total % 60;
                        return total === 0 ? '' : `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`;
                    });
                    setStartTimes(formattedTimes);
                    setHunt(ret.data);
                }
            })
            .catch(err => console.log(err));
    }, []);

    const navigate = useNavigate();

    return (
        <div className="bg-gray-100 p-6 rounded-lg shadow-lg max-w-3xl mx-auto">
            <h2 className="text-center text-2xl font-bold mb-6 text-gray-800">Hunt Management</h2>

            {/* Accordion */}
            <div className="space-y-6">
                {/* Hunt Details */}
                <div tabIndex={0} className="collapse collapse-arrow bg-white shadow rounded-lg">
                    <input type="checkbox" className="peer" />
                    <div className="collapse-title text-lg font-bold text-gray-800 bg-gray-200 peer-checked:bg-gray-300">
                        Hunt Details
                    </div>
                    <div className="collapse-content p-4 grid grid-cols-2 gap-4">
                        <div>
                            <label className="block font-semibold text-gray-700">Title:</label>
                            <input
                                type="text"
                                readOnly
                                value={hunt.name}
                                className="w-full bg-gray-100 border border-gray-300 rounded p-2 text-gray-800"
                            />
                        </div>
                        <div>
                            <label className="block font-semibold text-gray-700">Dates Held:</label>
                            <input
                                type="text"
                                readOnly
                                value={hunt.date}
                                className="w-full bg-gray-100 border border-gray-300 rounded p-2 text-gray-800"
                            />
                        </div>
                        <div>
                            <label className="block font-semibold text-gray-700">Time Interval:</label>
                            <input
                                type="text"
                                readOnly
                                value={hunt.timeInterval}
                                className="w-full bg-gray-100 border border-gray-300 rounded p-2 text-gray-800"
                            />
                        </div>
                    </div>
                </div>

                {/* Start Times */}
                <div tabIndex={0} className="collapse collapse-arrow bg-white shadow rounded-lg">
                    <input type="checkbox" className="peer" />
                    <div className="collapse-title text-lg font-bold text-gray-800 bg-gray-200 peer-checked:bg-gray-300">
                        Start Times
                    </div>
                    <div className="collapse-content p-4 grid grid-cols-4 gap-4">
                        {['Day Zero', 'Day One', 'Day Two', 'Day Three'].map((day, index) => (
                            <div key={index} className="flex flex-col items-center">
                                <label className="font-semibold text-gray-700">{day}</label>
                                <input
                                    type="time"
                                    readOnly
                                    value={startTimes[index]}
                                    className="bg-gray-100 border border-gray-300 rounded p-2 text-center text-gray-800"
                                />
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            {/* Action Buttons */}
            <div className="flex justify-center space-x-4 mt-6">
                <button
                    className="btn btn-primary"
                    onClick={() => navigate("/create-hunt")}
                >
                    New Hunt
                </button>
                <button
                    className="btn btn-warning"
                    onClick={() => navigate("/edit-hunt")}
                >
                    Edit Hunt Info
                </button>
                <button
                    className="btn btn-info"
                    onClick={() => navigate("/start-time")}
                >
                    Set Start Times
                </button>
            </div>
        </div>
    );
};

export default HuntManagement;
