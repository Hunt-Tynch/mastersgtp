import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getHunt, putHunt } from '../service/HuntService';

const EditHuntInfo = () => {
    const [hunt, setHunt] = useState({ name: '', date: '', timeInterval: '' });
    const navigate = useNavigate();

    useEffect(() => {
        getHunt()
            .then((ret) => {
                if (ret.data) setHunt(ret.data);
            })
            .catch((err) => console.log(err));
    }, []);

    const handleChange = (e) => setHunt({ ...hunt, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await putHunt(hunt);
            alert('Hunt information updated successfully!');
            navigate('/');
        } catch (error) {
            console.error('Failed to update hunt information:', error);
            alert('Error updating hunt information.');
        }
    };

    return (
        <div className="p-6 bg-gray-100 rounded-lg shadow-lg max-w-xl mx-auto">
            <h2 className="text-center text-2xl font-bold mb-6 text-gray-800">Edit Hunt Information</h2>

            <form onSubmit={handleSubmit} className="space-y-4">
                {/* Title Input */}
                <div>
                    <label className="block font-semibold text-gray-800 mb-2">Title:</label>
                    <input
                        type="text"
                        name="name"
                        value={hunt.name}
                        onChange={handleChange}
                        placeholder="Hunt Title"
                        className="input input-bordered w-full bg-white text-gray-900 border-gray-400 focus:outline-none focus:ring focus:ring-primary"
                    />
                </div>

                {/* Dates Held Input */}
                <div>
                    <label className="block font-semibold text-gray-800 mb-2">Dates Held:</label>
                    <input
                        type="text"
                        name="date"
                        value={hunt.date}
                        onChange={handleChange}
                        placeholder="Dates"
                        className="input input-bordered w-full bg-white text-gray-900 border-gray-400 focus:outline-none focus:ring focus:ring-primary"
                    />
                </div>

                {/* Time Interval Input */}
                <div>
                    <label className="block font-semibold text-gray-800 mb-2">Time Interval:</label>
                    <input
                        type="number"
                        name="timeInterval"
                        value={hunt.timeInterval}
                        onChange={handleChange}
                        placeholder="Time Interval (minutes)"
                        className="input input-bordered w-full bg-white text-gray-900 border-gray-400 focus:outline-none focus:ring focus:ring-primary"
                    />
                </div>

                {/* Submit Button */}
                <button
                    type="submit"
                    className="btn btn-primary w-full mt-4"
                >
                    Save Changes
                </button>
            </form>
        </div>
    );
};

export default EditHuntInfo;
