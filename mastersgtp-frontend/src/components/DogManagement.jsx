import React, { useEffect, useState } from 'react';
import {
    addDogsBatch,
    deleteDog,
    editDog,
    getAllDogs,
    getDogByNumber,
    getDogsByOwner,
    getDogsByTotalAndStake,
} from '../service/DogService';

const DogManagement = () => {
    const createEmptyDog = () => ({
        number: '',
        name: '',
        stake: '',
        regNumber: '',
        sire: '',
        dam: '',
        total: 0,
    });

    const [dogs, setDogs] = useState([]);
    const [newDogs, setNewDogs] = useState([createEmptyDog()]);
    const [ownerInfo, setOwnerInfo] = useState({
        owner: '',
        ownerTown: '',
        ownerState: '',
    });
    const [editMode, setEditMode] = useState(false);
    const [editDogNumber, setEditDogNumber] = useState(null);
    const [sireSuggestions, setSireSuggestions] = useState([]);
    const [damSuggestions, setDamSuggestions] = useState([]);
    const [filter, setFilter] = useState('');

    useEffect(() => {
        loadAllDogs();
    }, []);

    useEffect(() => {
        updateSuggestions(newDogs);
    }, [newDogs]);

    const loadAllDogs = async () => {
        try {
            const response = await getAllDogs();
            setDogs(response.data);
        } catch (error) {
            console.error('Error loading dogs:', error);
        }
    };

    const addDogRow = () => setNewDogs([...newDogs, createEmptyDog()]);

    const handleOwnerInputChange = (e) => {
        const { name, value } = e.target;
        setOwnerInfo({ ...ownerInfo, [name]: value });
    };

    const handleDogInputChange = (index, e) => {
        const { name, value } = e.target;
        const updatedDogs = [...newDogs];
        updatedDogs[index] = { ...updatedDogs[index], [name]: value };
        setNewDogs(updatedDogs);
    };

    const updateSuggestions = (dogs) => {
        const sires = Array.from(new Set(dogs.map((dog) => dog.sire).filter((sire) => sire)));
        const dams = Array.from(new Set(dogs.map((dog) => dog.dam).filter((dam) => dam)));
        setSireSuggestions(sires);
        setDamSuggestions(dams);
    };

    const handleAddDogs = async (e) => {
        e.preventDefault();
        const dogsWithOwnerInfo = newDogs.map((dog) => ({ ...dog, ...ownerInfo }));
        try {
            await addDogsBatch(dogsWithOwnerInfo);
            alert('Dogs added successfully!');
            setNewDogs([createEmptyDog()]);
            loadAllDogs();
        } catch (error) {
            console.error('Error adding dogs:', error);
        }
    };

    const handleEdit = (dog) => {
        setEditMode(true);
        setEditDogNumber(dog.number);
        setNewDogs([{ ...dog }]);
    };

    const handleUpdateDog = async (e) => {
        e.preventDefault();
        if (editDogNumber !== null) {
            try {
                newDogs[0].escores = [0, 0, 0, 0];
                newDogs[0].sdscores = [0, 0, 0, 0];
                await editDog(newDogs[0]);
                alert('Dog updated successfully!');
                setEditMode(false);
                setEditDogNumber(null);
                setNewDogs([createEmptyDog()]);
                loadAllDogs();
            } catch (error) {
                console.error('Error updating dog:', error);
            }
        }
    };

    const handleDelete = async (number) => {
        if (window.confirm('Are you sure you want to delete this dog?')) {
            try {
                await deleteDog(number);
                alert('Dog deleted successfully!');
                loadAllDogs();
            } catch (error) {
                console.error('Error deleting dog:', error);
            }
        }
    };

    const handleRemoveDog = (index) => {
        setNewDogs(newDogs.filter((_, i) => i !== index));
    };

    const handleFilterChange = async (type, value) => {
        try {
            if (type === 'number') {
                const response = await getDogByNumber(value);
                setDogs(response.data ? [response.data] : []);
            } else if (type === 'owner') {
                const response = await getDogsByOwner(value);
                setDogs(response.data);
            } else if (type === 'stake') {
                const response = await getDogsByTotalAndStake(value);
                setDogs(response.data);
            } else {
                loadAllDogs();
            }
        } catch (error) {
            console.error('Error filtering dogs:', error);
        }
    };

    return (
        <div className="p-6 bg-gray-100 rounded-lg shadow-lg max-w-6xl mx-auto text-black">
            <h2 className="text-2xl font-bold text-center mb-6">Dog Management</h2>
            <div className="collapse collapse-arrow bg-gray-50 border" style={{marginBottom: '15px'}}>
                <input type="checkbox" />
                <h3 className="collapse-title font-bold text-lg mb-4">Add Dogs</h3>
            {/* Owner Information Section */}
            <div className="collapse-content">
            <div className="border border-gray-300 rounded-lg mb-4 p-4">
                <h3 className="font-bold text-lg mb-4">Owner Information</h3>
                <div className="grid grid-cols-3 gap-4">
                    <div>
                        <label className="block font-semibold text-gray-700 mb-2">Owner</label>
                        <input
                            type="text"
                            name="owner"
                            value={ownerInfo.owner}
                            onChange={handleOwnerInputChange}
                            className="input input-bordered w-full bg-white border-black text-black"
                            placeholder="Owner"
                        />
                    </div>
                    <div>
                        <label className="block font-semibold text-gray-700 mb-2">Town</label>
                        <input
                            type="text"
                            name="ownerTown"
                            value={ownerInfo.ownerTown}
                            onChange={handleOwnerInputChange}
                            className="input input-bordered w-full bg-white border-black text-black"
                            placeholder="Owner Town"
                        />
                    </div>
                    <div>
                        <label className="block font-semibold text-gray-700 mb-2">State</label>
                        <input
                            type="text"
                            name="ownerState"
                            value={ownerInfo.ownerState}
                            onChange={handleOwnerInputChange}
                            className="input input-bordered w-full bg-white border-black text-black"
                            placeholder="Owner State"
                        />
                    </div>
                </div>
            </div>
            {/* Dog Information Section */}
            <div className="border border-gray-300 rounded-lg mb-4 p-4">
                <h3 className="font-bold text-lg mb-4">{editMode ? 'Edit Dog' : 'Add Dogs'}</h3>
                <form onSubmit={editMode ? handleUpdateDog : handleAddDogs}>
                    {newDogs.map((dog, index) => (
                        <div key={index} className="grid grid-cols-7 gap-4 items-center mb-4">
                            <input
                                type="number"
                                name="number"
                                value={dog.number}
                                onChange={(e) => handleDogInputChange(index, e)}
                                placeholder="#"
                                className="input input-bordered bg-white border-black"
                                required
                            />
                            <input
                                type="text"
                                name="name"
                                value={dog.name}
                                onChange={(e) => handleDogInputChange(index, e)}
                                placeholder="Dog Name"
                                className="input input-bordered bg-white border-black"
                                required
                            />
                            <select
                                name="stake"
                                value={dog.stake}
                                onChange={(e) => handleDogInputChange(index, e)}
                                className="select select-bordered bg-white border-black"
                                required
                            >
                                <option value="">Select Stake</option>
                                <option value="DERBY">DERBY</option>
                                <option value="ALL_AGE">ALL_AGE</option>
                            </select>
                            <input
                                type="text"
                                name="regNumber"
                                value={dog.regNumber}
                                onChange={(e) => handleDogInputChange(index, e)}
                                placeholder="Reg Number"
                                className="input input-bordered bg-white border-black"
                            />
                            <input
                                type="text"
                                name="sire"
                                value={dog.sire}
                                onChange={(e) => handleDogInputChange(index, e)}
                                placeholder="Sire"
                                className="input input-bordered bg-white border-black"
                            />
                            <input
                                type="text"
                                name="dam"
                                value={dog.dam}
                                onChange={(e) => handleDogInputChange(index, e)}
                                placeholder="Dam"
                                className="input input-bordered bg-white border-black"
                            />
                            <button
                                type="button"
                                className="btn btn-error"
                                onClick={() => handleRemoveDog(index)}
                            >
                                Remove
                            </button>
                        </div>
                    ))}
                    <button
                        type="button"
                        onClick={addDogRow}
                        className="btn btn-success w-full mb-4"
                        disabled={editMode}
                    >
                        Add Row
                    </button>
                    <button type="submit" className="btn btn-primary w-full">
                        {editMode ? 'Update Dog' : 'Add All Dogs'}
                    </button>
                </form>
            </div>
            </div>
            </div>
            {/* Filtering Options */}
            <div className="flex justify-between mb-4">
                <button
                    onClick={() => handleFilterChange('all', '')}
                    className="btn btn-info"
                >
                    View All Dogs
                </button>
                <button
                    onClick={() => handleFilterChange('owner', ownerInfo.owner)}
                    className="btn btn-info"
                >
                    View Dogs by Owner
                </button>
                <button
                    onClick={() => handleFilterChange('stake', 'DERBY')}
                    className="btn btn-info"
                >
                    View DERBY Dogs
                </button>
                <button
                    onClick={() => handleFilterChange('stake', 'ALL_AGE')}
                    className="btn btn-info"
                >
                    View ALL_AGE Dogs
                </button>
                <input
                    type="number"
                    placeholder="Search by Dog #"
                    onChange={(e) => handleFilterChange('number', e.target.value)}
                    className="input input-bordered w-40 bg-white"
                />
            </div>

            {/* Dog List */}
            <div className="collapse collapse-arrow bg-gray-200 rounded-md p-4 shadow-lg">
                <input type="checkbox" />
                <h4 className="collapse-title text-center font-bold mb-4">Dog List</h4>
                <div className="collapse-content overflow-x-auto bg-gray-100">
                    <table className="table w-full">
                        <thead>
                            <tr>
                                <th className="text-black">#</th>
                                <th className="text-black">Name</th>
                                <th className="text-black">Stake</th>
                                <th className="text-black">Total</th>
                                <th className="text-black">Scratched</th>
                                <th className="text-black">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {dogs.map((dog) => (
                                <tr key={dog.number}>
                                    <td>{dog.number}</td>
                                    <td>{dog.name}</td>
                                    <td>{dog.stake}</td>
                                    <td>{dog.total}</td>
                                    <td>{dog.scratched ? '✔' : ''}</td>
                                    <td>
                                        <button
                                            className="btn btn-warning btn-xs mr-2"
                                            onClick={() => handleEdit(dog)}
                                        >
                                            Edit
                                        </button>
                                        <button
                                            className="btn btn-error btn-xs"
                                            onClick={() => handleDelete(dog.number)}
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
        </div>
    );
};

export default DogManagement;
