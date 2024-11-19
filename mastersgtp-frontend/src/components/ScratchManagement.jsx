import React, { useEffect, useState } from 'react';
import { createScratch, deleteScratch, getScratches } from '../service/ScratchService';

const ScratchManagement = () => {
    const [scratches, setScratches] = useState([]);
    const [newScratch, setNewScratch] = useState({
        judge: { number: '' },
        dog: { number: '' },
        reason: '',
        time: '00:00', // Set as HH:MM format for form input
    });

    // Helper functions to convert between minutes and HH:MM format
    const timeToMinutes = (time) => {
        const [hours, minutes] = time.split(':').map(Number);
        return hours * 60 + minutes;
    };

    const minutesToTime = (minutes) => {
        const hours = String(Math.floor(minutes / 60)).padStart(2, '0');
        const mins = String(minutes % 60).padStart(2, '0');
        return `${hours % 12 === 0 ? '12' : hours % 12}:${mins} ${hours < 12 ? 'AM' : 'PM'}`;
    };

    const fetchScratches = async () => {
        try {
            const response = await getScratches();
            const formattedScratches = response.data.map((scratch) => ({
                ...scratch,
                time: minutesToTime(scratch.time), // Convert time to HH:MM format for display
            }));
            setScratches(formattedScratches);
        } catch (error) {
            console.error("Failed to fetch scratches:", error);
        }
    };

    useEffect(() => {
        fetchScratches();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;

        if (name === "judgeNumber") {
            setNewScratch((prevState) => ({
                ...prevState,
                judge: { ...prevState.judge, number: value },
            }));
        } else if (name === "dogNumber") {
            setNewScratch((prevState) => ({
                ...prevState,
                dog: { ...prevState.dog, number: value },
            }));
        } else {
            setNewScratch((prevState) => ({
                ...prevState,
                [name]: value,
            }));
        }
    };

    const handleCreateScratch = async (e) => {
        e.preventDefault();
        try {
            const scratchData = {
                ...newScratch,
                time: timeToMinutes(newScratch.time), // Convert time to minutes before sending to backend
            };
            await createScratch(scratchData);
            setNewScratch({ judge: { number: '' }, dog: { number: '' }, reason: '', time: '00:00' });
            fetchScratches();
        } catch (error) {
            console.error("Failed to create scratch:", error);
        }
    };

    const handleDeleteScratch = async (id) => {
        if (window.confirm("Are you sure you want to delete this scratch?")) {
            try {
                await deleteScratch(id);
                fetchScratches();
            } catch (error) {
                console.error("Failed to delete scratch:", error);
            }
        }
    };

    return (
        <div className="p-6 bg-gray-100 rounded-lg shadow-lg max-w-5xl mx-auto text-black">
            <h2 className="text-2xl font-bold text-center mb-6">Scratch Management</h2>

            {/* Scratch Form */}
            <form onSubmit={handleCreateScratch} className="collapse collapse-arrow p-4 bg-white rounded-lg shadow mb-6">
                <input type="checkbox"></input>
                <h3 className="collapse-title font-bold text-lg mb-4 text-center">Add New Scratch</h3>
                <div className="collapse-content grid grid-cols-2 gap-4">
                    <div>
                        <label className="block font-bold mb-2">Judge Number</label>
                        <input
                            type="number"
                            name="judgeNumber"
                            value={newScratch.judge.number}
                            onChange={handleInputChange}
                            className="input input-bordered w-full bg-white border-black"
                            placeholder="Judge Number"
                            required
                        />
                    </div>
                    <div>
                        <label className="block font-bold mb-2">Dog Number</label>
                        <input
                            type="number"
                            name="dogNumber"
                            value={newScratch.dog.number}
                            onChange={handleInputChange}
                            className="input input-bordered w-full bg-white border-black"
                            placeholder="Dog Number"
                            required
                        />
                    </div>
                    <div>
                        <label className="block font-bold mb-2">Reason</label>
                        <input
                            type="text"
                            name="reason"
                            value={newScratch.reason}
                            onChange={handleInputChange}
                            className="input input-bordered w-full bg-white border-black"
                            placeholder="Reason"
                            required
                        />
                    </div>
                    <div>
                        <label className="block font-bold mb-2">Time (HH:MM)</label>
                        <input
                            type="time"
                            name="time"
                            value={newScratch.time}
                            onChange={handleInputChange}
                            className="input input-bordered w-full bg-white border-black"
                            required
                        />
                    </div>
                    <button type="submit" className="btn col-span-2 btn-primary w-full mt-4">
                    Add Scratch
                </button>
                </div>
            </form>

            {/* Scratch List */}
            <h3 className="font-bold text-lg text-center mb-4">Existing Scratches</h3>
            <div className="overflow-x-auto bg-gray-50 rounded-lg shadow">
                <table className="table w-full">
                    <thead>
                        <tr>
                            <th className="text-black">Judge</th>
                            <th className="text-black">Dog</th>
                            <th className="text-black">Reason</th>
                            <th className="text-black">Time</th>
                            <th className="text-black">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {scratches.map((scratch) => (
                            <tr key={scratch.id}>
                                <td>{scratch.judge.number}</td>
                                <td>{scratch.dog.number}</td>
                                <td>{scratch.reason}</td>
                                <td>{scratch.time}</td>
                                <td>
                                    <button
                                        className="btn btn-error btn-xs"
                                        onClick={() => handleDeleteScratch(scratch.id)}
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ScratchManagement;
