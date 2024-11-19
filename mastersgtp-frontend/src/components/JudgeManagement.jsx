import React, { useEffect, useState } from 'react';
import { deleteJudge, getJudges, postJudge, putJudge } from '../service/JudgeService';

const JudgeManagement = () => {
    const [judges, setJudges] = useState([]);
    const [newJudge, setNewJudge] = useState({ name: '', pin: '', number: '' });
    const [editMode, setEditMode] = useState(false);
    const [editJudgeNumber, setEditJudgeNumber] = useState(null);

    useEffect(() => {
        loadJudges();
    }, []);

    const loadJudges = async () => {
        try {
            const response = await getJudges();
            setJudges(response.data);
        } catch (error) {
            console.error("Error loading judges:", error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewJudge({ ...newJudge, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editMode) {
                await putJudge(editJudgeNumber, newJudge);
                alert("Judge updated successfully!");
            } else {
                await postJudge(newJudge);
                alert("Judge added successfully!");
            }
            setNewJudge({ name: '', pin: '', number: '' });
            setEditMode(false);
            setEditJudgeNumber(null);
            loadJudges();
        } catch (error) {
            console.error("Error saving judge:", error);
        }
    };

    const handleEdit = (judge) => {
        setEditMode(true);
        setEditJudgeNumber(judge.number);
        setNewJudge({ name: judge.name, pin: judge.pin, number: judge.number });
    };

    const handleDelete = async (number) => {
        if (window.confirm("Are you sure you want to delete this judge?")) {
            try {
                await deleteJudge(number);
                alert("Judge deleted successfully!");
                loadJudges();
            } catch (error) {
                console.error("Error deleting judge:", error);
            }
        }
    };

    return (
        <div className="p-6 bg-gray-100 rounded-lg shadow-lg max-w-4xl mx-auto text-black">
            <h2 className="text-2xl font-bold text-center mb-6">Judge Management</h2>

            {/* Add/Edit Judge Form */}
            <form onSubmit={handleSubmit} className="collapse collapse-arrow p-4 bg-white rounded-lg shadow">
                <input type="checkbox"></input>
                <h4 className="collapse-title text-center font-bold mb-4">{editMode ? "Edit Judge" : "Add New Judge"}</h4>
                <div className="collapse-content grid grid-cols-1 gap-4">
                    <div>
                        <label className="block font-bold mb-2">Number:</label>
                        <input
                            type="number"
                            name="number"
                            value={newJudge.number}
                            onChange={handleInputChange}
                            placeholder="Judge Number"
                            className="input input-bordered w-full bg-white border-black"
                            required
                            readOnly={editMode}
                        />
                    </div>
                    <div>
                        <label className="block font-bold mb-2">Name:</label>
                        <input
                            type="text"
                            name="name"
                            value={newJudge.name}
                            onChange={handleInputChange}
                            placeholder="Judge Name"
                            className="input input-bordered w-full bg-white border-black"
                            required
                        />
                    </div>
                    <div>
                        <label className="block font-bold mb-2">PIN:</label>
                        <input
                            type="text"
                            name="pin"
                            value={newJudge.pin}
                            onChange={handleInputChange}
                            placeholder="Judge PIN"
                            className="input input-bordered w-full bg-white border-black"
                            required
                        />
                    </div>
                    <button type="submit" className="btn btn-primary w-full mt-4">
                    {editMode ? "Update Judge" : "Add Judge"}
                </button>
                </div>
            </form>

            {/* Judge List */}
            <div className="overflow-x-auto mb-6">
                <table className="table w-full">
                    <thead>
                        <tr>
                            <th className="text-black">Number</th>
                            <th className="text-black">Name</th>
                            <th className="text-black">PIN</th>
                            <th className="text-black">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {judges.map((judge) => (
                            <tr key={judge.number}>
                                <td>{judge.number}</td>
                                <td>{judge.name}</td>
                                <td>{judge.pin}</td>
                                <td>
                                    <button
                                        className="btn btn-warning btn-xs mr-2"
                                        onClick={() => handleEdit(judge)}
                                    >
                                        Edit
                                    </button>
                                    <button
                                        className="btn btn-error btn-xs"
                                        onClick={() => handleDelete(judge.number)}
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

export default JudgeManagement;
