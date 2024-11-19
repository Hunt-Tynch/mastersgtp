import React, { useEffect, useState } from 'react';
import { deleteCross, getAllCrossForDay, getCrossByDog, getCrossByJudge, postCross, putCross } from '../service/CrossService';

const CrossManagement = () => {
    const [crosses, setCrosses] = useState([]);
    const [newCross, setNewCross] = useState({ judgeNumber: '', dogPoints: '', day: '', crossTime: '' });
    const [editMode, setEditMode] = useState(false);
    const [editCrossId, setEditCrossId] = useState(null);
    const [selectedDay, setSelectedDay] = useState('');

    useEffect(() => {
        if (selectedDay) {
            loadCrossesForDay(selectedDay);
        }
    }, [selectedDay]);

    const loadCrossesForDay = async (day) => {
        try {
            const response = await getAllCrossForDay(day);
            setCrosses(response.data);
        } catch (error) {
            console.error("Error loading crosses for the selected day:", error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewCross({ ...newCross, [name]: value });
    };

    const timeToMinutes = (time) => {
        const [hours, minutes] = time.split(':').map(Number);
        return hours * 60 + minutes;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const dogPointsMap = newCross.dogPoints.split(',').reduce((acc, entry) => {
            const [dogNumber, points] = entry.split(':').map(str => str.trim());
            if (dogNumber && points) {
                acc.push([{ number: parseInt(dogNumber) }, parseInt(points)]);
            }
            return acc;
        }, []);
        
        const crossData = {
            judge: { number: parseInt(newCross.judgeNumber) },
            dogs: dogPointsMap,
            day: parseInt(newCross.day),
            crossTime: timeToMinutes(newCross.crossTime),
        };

        try {
            if (editMode) {
                await putCross(editCrossId, crossData);
                alert("Cross updated successfully!");
            } else {
                await postCross(crossData);
                alert("Cross added successfully!");
            }
            setNewCross({ judgeNumber: '', dogPoints: '', day: '', crossTime: '' });
            setEditMode(false);
            setEditCrossId(null);
            if (selectedDay) {
                loadCrossesForDay(selectedDay);
            }
        } catch (error) {
            console.error("Error saving cross:", error);
        }
    };

    const handleEdit = (cross) => {
        setEditMode(true);
        setEditCrossId(cross.id);

        const dogPointsString = cross.dogs
            .map((entry) => `${entry.dog.number}:${entry.points}`)
            .join(', ');

        setNewCross({
            judgeNumber: cross.judge.number,
            dogPoints: dogPointsString,
            day: cross.day,
            crossTime: `${String(Math.floor(cross.crossTime / 60)).padStart(2, '0')}:${String(cross.crossTime % 60).padStart(2, '0')}`
        });
    };

    const handleDelete = async (id) => {
        if (window.confirm("Are you sure you want to delete this cross?")) {
            try {
                await deleteCross(id);
                alert("Cross deleted successfully!");
                if (selectedDay) {
                    loadCrossesForDay(selectedDay);
                }
            } catch (error) {
                console.error("Error deleting cross:", error);
            }
        }
    };

    const handleFilterByJudge = async (number) => {
        try {
            const response = await getCrossByJudge(number);
            setCrosses(response.data);
        } catch (error) {
            console.error("Error filtering crosses by judge:", error);
        }
    };

    const handleFilterByDog = async (number) => {
        try {
            const response = await getCrossByDog(number);
            setCrosses(response.data);
        } catch (error) {
            console.error("Error filtering crosses by dog:", error);
        }
    };

    return (
        <div className="p-6 bg-gray-100 rounded-lg shadow-lg max-w-6xl mx-auto text-black">
            <h2 className="text-2xl font-bold text-center mb-6">Cross Management</h2>

            <form onSubmit={handleSubmit} className="collapse collapse-arrow p-4 bg-white rounded-lg shadow mb-6">
                <input type="checkbox"></input>
                <h3 className="collapse-title font-bold text-lg mb-4">Add Cross</h3>
                <div className="collapse-content grid grid-cols-1 gap-4">
                    <div>
                        <label className="block font-bold mb-2">Judge Number:</label>
                        <input
                            type="text"
                            name="judgeNumber"
                            value={newCross.judgeNumber}
                            onChange={handleInputChange}
                            placeholder="Enter Judge Number"
                            className="input input-bordered w-full bg-white"
                            required
                        />
                    </div>
                    <div>
                        <label className="block font-bold mb-2">Dog Numbers and Points:</label>
                        <input
                            type="text"
                            name="dogPoints"
                            value={newCross.dogPoints}
                            onChange={handleInputChange}
                            placeholder="Enter Dog Numbers and Points (e.g., 1:10, 2:15)"
                            className="input input-bordered w-full bg-white"
                            required
                        />
                    </div>
                    <div>
                        <label className="block font-bold mb-2">Day:</label>
                        <input
                            type="number"
                            name="day"
                            min={0}
                            max={3}
                            value={newCross.day}
                            onChange={handleInputChange}
                            placeholder="Enter Day"
                            className="input input-bordered w-full bg-white"
                            required
                        />
                    </div>
                    <div>
                        <label className="block font-bold mb-2">Cross Time:</label>
                        <input
                            type="time"
                            name="crossTime"
                            value={newCross.crossTime}
                            onChange={handleInputChange}
                            className="input input-bordered w-full bg-white"
                            required
                        />
                    </div>
                    <button type="submit" className="btn btn-primary w-full mt-4">
                    {editMode ? "Update Cross" : "Add Cross"}
                </button>
                </div>
            </form>

            <div className="flex justify-between">
                <input
                    type="number"
                    placeholder="Filter by Day"
                    min={0}
                    max={3}
                    onChange={(e) => setSelectedDay(e.target.value)}
                    className="input input-bordered w-1/4 bg-white"
                />
                <input
                    type="text"
                    placeholder="Filter by Judge Number"
                    onChange={(e) => handleFilterByJudge(e.target.value)}
                    className="input input-bordered w-1/4 bg-white"
                />
                <input
                    type="text"
                    placeholder="Filter by Dog Number"
                    onChange={(e) => handleFilterByDog(e.target.value)}
                    className="input input-bordered w-1/4 bg-white"
                />
            </div>

            <div className="overflow-x-auto mb-6">
                <table className="table w-full">
                    <thead>
                        <tr>
                            <th className="text-black">Judge Number</th>
                            <th className="text-black">Dogs</th>
                            <th className="text-black">Day</th>
                            <th className="text-black">Cross Time (HH:MM)</th>
                            <th className="text-black">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {crosses.map((cross) => (
                            <tr key={cross.id}>
                                <td>{cross.judge.number}</td>
                                <td>{cross.dogs.map((entry) => `${entry.dog.number}`).join(', ')}</td>
                                <td>{cross.day}</td>
                                <td>{`${String(Math.floor(cross.crossTime / 60)).padStart(2, '0')}:${String(cross.crossTime % 60).padStart(2, '0')}`}</td>
                                <td>
                                    <button
                                        className="btn btn-warning btn-xs mr-2"
                                        onClick={() => handleEdit(cross)}
                                    >
                                        Edit
                                    </button>
                                    <button
                                        className="btn btn-error btn-xs"
                                        onClick={() => handleDelete(cross.id)}
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

export default CrossManagement;
