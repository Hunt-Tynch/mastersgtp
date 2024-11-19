import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { putStartTime } from '../service/HuntService';

const SetStartTime = () => {
    const [day, setDay] = useState('');
    const [startTime, setStartTime] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const [hours, minutes] = startTime.split(':').map(Number);
        const timeInMinutes = hours * 60 + minutes;
        try {
            await putStartTime(day, timeInMinutes);
            alert('Start time updated successfully!');
            navigate('/');
        } catch (error) {
            console.error('Failed to set start time:', error);
            alert('Error setting start time.');
        }
    };

    return (
        <div className="p-6 bg-gray-100 rounded-lg shadow-lg max-w-md mx-auto">
            <h2 className="text-center text-2xl font-bold mb-6 text-gray-800">Set Start Time</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
                {/* Day Selection */}
                <div>
                    <label className="block font-semibold text-gray-800 mb-2">Select Day:</label>
                    <select
                        value={day}
                        onChange={(e) => setDay(e.target.value)}
                        required
                        className="select select-bordered w-full bg-white text-gray-900 border-gray-400 focus:outline-none focus:ring focus:ring-primary"
                    >
                        <option value="">Choose Day...</option>
                        <option value="0">Day Zero</option>
                        <option value="1">Day One</option>
                        <option value="2">Day Two</option>
                        <option value="3">Day Three</option>
                    </select>
                </div>

                {/* Start Time Input */}
                <div>
                    <label className="block font-semibold text-gray-800 mb-2">Start Time:</label>
                    <input
                        type="time"
                        value={startTime}
                        onChange={(e) => setStartTime(e.target.value)}
                        required
                        className="input input-bordered w-full bg-white text-gray-900 border-gray-400 focus:outline-none focus:ring focus:ring-primary"
                    />
                </div>

                {/* Submit Button */}
                <button type="submit" className="btn btn-primary w-full">
                    Set Start Time
                </button>
            </form>
        </div>
    );
};

export default SetStartTime;
