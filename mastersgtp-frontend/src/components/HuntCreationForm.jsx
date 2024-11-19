import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { postHunt } from '../service/HuntService';

const HuntCreationForm = () => {
    const [title, setTitle] = useState('');
    const [datesHeld, setDatesHeld] = useState('');
    const [timeInterval, setTimeInterval] = useState('');
    const [startTimes, setStartTimes] = useState({
        dayZero: '00:00',
        dayOne: '00:00',
        dayTwo: '00:00',
        dayThree: '00:00',
    });
    const navigate = useNavigate();

    function timeToMinutes(time) {
        const [hours, minutes] = time.split(':').map(Number);
        return hours * 60 + minutes;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        const huntData = {
            name: title,
            date: datesHeld,
            timeInterval,
            startTimes: [
                timeToMinutes(startTimes.dayZero),
                timeToMinutes(startTimes.dayOne),
                timeToMinutes(startTimes.dayTwo),
                timeToMinutes(startTimes.dayThree),
            ],
        };
        try {
            await postHunt(huntData);
            alert('Hunt created successfully!');
            setTitle('');
            setDatesHeld('');
            setTimeInterval('');
            setStartTimes({ dayZero: '00:00', dayOne: '00:00', dayTwo: '00:00', dayThree: '00:00' });
            navigate('/');
        } catch (error) {
            console.error('Error creating hunt:', error);
            alert('Failed to create hunt. Please try again.');
        }
    };

    return (
        <div className="p-6 bg-gray-100 rounded-lg shadow-lg max-w-3xl mx-auto">
            <h2 className="text-center text-2xl font-bold mb-6 text-gray-800">Create New Hunt</h2>

            <form onSubmit={handleSubmit}>
                {/* Title Input */}
                <div className="mb-4">
                    <label className="block font-semibold mb-2 text-gray-800">Title:</label>
                    <input
                        type="text"
                        placeholder="Hunt Title"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        className="input input-bordered w-full bg-white text-gray-900 border-gray-400 focus:outline-none focus:ring focus:ring-primary"
                    />
                </div>

                {/* Dates Held Input */}
                <div className="mb-4">
                    <label className="block font-semibold mb-2 text-gray-800">Dates Held:</label>
                    <input
                        type="text"
                        placeholder="Dates"
                        value={datesHeld}
                        onChange={(e) => setDatesHeld(e.target.value)}
                        className="input input-bordered w-full bg-white text-gray-900 border-gray-400 focus:outline-none focus:ring focus:ring-primary"
                    />
                </div>

                {/* Time Interval Input */}
                <div className="mb-4">
                    <label className="block font-semibold mb-2 text-gray-800">Time Interval:</label>
                    <input
                        type="number"
                        placeholder="Time Interval (minutes)"
                        value={timeInterval}
                        onChange={(e) => setTimeInterval(e.target.value)}
                        className="input input-bordered w-full bg-white text-gray-900 border-gray-400 focus:outline-none focus:ring focus:ring-primary"
                    />
                </div>

                {/* Start Times Section */}
                <div tabIndex={0} className="collapse collapse-arrow bg-gray-200 rounded-lg">
                    <input type="checkbox" className="peer" />
                    <div className="collapse-title font-semibold peer-checked:bg-gray-300 text-gray-800">
                        Start Times
                    </div>
                    <div className="collapse-content bg-gray-25 peer-checked:block p-4 rounded-lg">
                        <div className="grid grid-cols-2 gap-4">
                            {['dayZero', 'dayOne', 'dayTwo', 'dayThree'].map((day, index) => (
                                <div key={index} className="flex flex-col items-center">
                                    <label className="font-semibold mb-2 text-gray-800">{`Day ${index}`}</label>
                                    <input
                                        type="time"
                                        value={startTimes[day]}
                                        onChange={(e) =>
                                            setStartTimes({ ...startTimes, [day]: e.target.value })
                                        }
                                        className="input input-bordered text-center w-full bg-white text-gray-900 border-gray-400 focus:outline-none focus:ring focus:ring-primary"
                                    />
                                </div>
                            ))}
                        </div>
                    </div>
                </div>

                {/* Submit Button */}
                <button type="submit" className="btn btn-primary w-full mt-6">
                    Create Hunt
                </button>
            </form>
        </div>
    );
};

export default HuntCreationForm;
