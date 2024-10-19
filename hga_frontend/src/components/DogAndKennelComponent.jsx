import React, { useEffect, useState } from "react";
import { deleteDog, getAllDogs, getDogsByKennel, getDogsByStake, postDogs } from "../services/DogService";
import { listHunt } from "../services/HuntService";
import { deleteKennel, listKennel, makeKennel } from "../services/KennelService";

const DogAndKennelComponent = () => {
    const [hunt, setHunt] = useState([]);
    const [kennels, setKennels] = useState([]);
    const [selectedKennel, setSelectedKennel] = useState({ id: null });
    const [name, setName] = useState("");
    const [town, setTown] = useState("");
    const [state, setState] = useState("");
    const [searchTerm, setSearchTerm] = useState("");
    const [dogs, setDogs] = useState([]);
    const [dogEntries, setDogEntries] = useState([]);
    const [lastDogEntry, setLastDogEntry] = useState({ stake: "", sire: "", dam: "" });
    const [stakeOptions] = useState(["ALL_AGE", "DERBY"]);
    const [showMessage, setShowMessage] = useState(false)
    const [selectedStake, setSelectedStake] = useState(null)

    useEffect(() => {
        listHunt().then(ret => {
            setHunt(ret.data);
        }).catch(err => {
            console.log(err);
        });

        listKennel()
            .then(ret => {
                setKennels(ret.data);
            })
            .catch(err => {
                console.log(err);
            });
    }, []);

    const handleMouseEnter = () => {
        setShowMessage(true);
    };

    const handleMouseLeave = () => {
        setShowMessage(false);
    };

    const handleSelect = (kennel) => {
        setSelectedKennel(kennel);
        getDogsByKennel(kennel.id).then(ret => {
            setDogs(ret.data);
        }).catch(err => {
            console.log(err);
        });
    };

    const handleDelete = (kennel) => {
        deleteKennel(kennel.id).then(() => {
            listKennel().then(ret => {
                setKennels(ret.data)
            }).catch(err => {
                console.log(err)
            })
            setSelectedKennel({ id: null })
            if (selectedStake) {
                getStake(selectedStake)
            }
        }).catch(err => {
            console.log(err);
        });
    };

    const addKennel = () => {
        const kennel = {
            owner: name,
            town: town,
            state: state
        };
        makeKennel(kennel).then(ret => {
            listKennel().then(ret => {
                setKennels(ret.data);
            }).catch(err => {
                console.log(err);
            });
            setSelectedKennel(ret.data);
        }).catch(err => {
            console.log(err);
        });
    };

    const addDogs = () => {
        if (!selectedKennel.id || dogEntries.length === 0) return;

        const dogsToAdd = dogEntries.map(dog => ({
            hunt: hunt,
            number: dog.number,
            stake: dog.stake,
            name: dog.name,
            regNumber: dog.regNumber,
            sire: dog.sire,
            dam: dog.dam,
            kennel: selectedKennel
        }));

        postDogs(dogsToAdd).then(() => {
            getDogsByKennel(selectedKennel.id).then(ret => {
                setDogs(ret.data);
            }).catch(err => {
                console.log(err);
            });
            setDogEntries([]); // Clear the table after adding
        }).catch(err => {
            console.log(err);
        });
    };

    const addDogEntry = () => {
        const newEntry = {
            hunt: "",
            number: "",
            stake: lastDogEntry.stake || "ALL_AGE",
            name: "",
            regNumber: "",
            sire: lastDogEntry.sire || "",
            dam: lastDogEntry.dam || ""
        };
        setDogEntries([...dogEntries, newEntry]);
        setLastDogEntry(newEntry); // Update last dog entry to the new one
    };

    const removeDogEntry = (index) => {
        const newEntries = dogEntries.filter((_, i) => i !== index);
        setDogEntries(newEntries);
    };

    const handleDogEntryChange = (index, field, value) => {
        const newEntries = [...dogEntries];
        newEntries[index][field] = value;
        setDogEntries(newEntries);

        if (field === 'stake' || field === 'sire' || field === 'dam') {
            setLastDogEntry(prev => ({ ...prev, [field]: value })); // Update last dog entry
        }
    };

    const filteredKennels = kennels.filter(kennel => 
        kennel.owner.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const removeDog = (id) => {
        deleteDog(id).then(() => {
            getDogsByKennel(selectedKennel.id).then(ret => {
                setDogs(ret.data);
            }).catch(err => {
                console.log(err);
            });
        }).catch(err => {
            console.log(err)
        })
    }

    const getStake = (stake) => {
        getDogsByStake(stake).then(ret => {
            setDogs(ret.data)
        }).catch(err => {
            console.log(err)
        })
        setSelectedStake(stake)
        setSelectedKennel({ id: null})
    }

    const getAll = () => {
        getAllDogs().then(ret => {
            setDogs(ret.data)
        }).catch(err => {
            console.log(err)
        })
        setSelectedStake('ALL')
        setSelectedKennel({ id: null})
    }

    return (
        <div className="container d-flex justify-content-center align-items-start">
            <div className="kennel-section" style={{ flex: 1, marginRight: '20px', background: '#c0c0c0', border: '3px solid black', borderRadius: '5px', padding: '10px', maxWidth: '400px' }}>
                <div className="text-center">
                    <div className="large-text">Kennels</div>
                    <input
                        type="text"
                        placeholder="Search by Name"
                        onChange={e => setSearchTerm(e.target.value)}
                        style={{ margin: '5px 0', width: '75%' }}
                    />
                    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        <input type="text" placeholder="Name" onChange={e => setName(e.target.value)} style={{ margin: '5px 0' }} />
                        <input type="text" placeholder="Town" onChange={e => setTown(e.target.value)} style={{ margin: '5px 0' }} />
                        <input type="text" placeholder="State" onChange={e => setState(e.target.value)} style={{ margin: '5px 0' }} />
                        <button className="btn btn-secondary" onClick={addKennel}>Add Kennel</button>
                    </div>
                </div>
                <div className="kennels-list" style={{ display: 'flex', overflowX: 'auto', padding: '10px', whiteSpace: 'nowrap' }}>
                    {filteredKennels.length > 0 ? (
                        filteredKennels.map((kennel) => (
                            <div key={kennel.id} className="kennel-item text-center" style={{ marginRight: '20px', border: selectedKennel.id === kennel.id ? '5px solid blue' : '5px solid black', borderRadius: '3px', padding: '5px' }}>
                                <h4>{kennel.owner}</h4>
                                <p>{`${kennel.town}, ${kennel.state}`}</p>
                                <button className="btn btn-secondary mx-2" onClick={() => handleSelect(kennel)}>Select</button>
                                <button className="btn btn-danger mx-2" onClick={() => handleDelete(kennel)}>Delete</button>
                            </div>
                        ))
                    ) : (
                            <p>No kennels available.</p>
                    )}
                </div>
                <div className="row row-cols-1 align-items-center justify-content-center">
                    <button className="btn btn-secondary mb-2 mx-2 col-auto" onClick={() => getStake("ALL_AGE")}>View ALL_AGE Dogs</button>
                    <button className="btn btn-secondary mb-2 mx-2 col-auto" onClick={() => getStake("DERBY")}>View DERBY Dogs</button>
                    <button className="btn btn-secondary mb-2 mx-2 col-auto" onClick={getAll}>View ALL Dogs</button>
                </div>
            </div>

            {selectedKennel.id && (
                <div className="dog-section" style={{ flex: 2, background: '#c0c0c0', border: '3px solid black', borderRadius: '5px', padding: '10px', minWidth: '1250px' }}>
                    <div className="text-center">
                        <h4 className="medium-text">Dogs for {selectedKennel.owner}</h4>
                        <button className="btn btn-secondary mb-2 mx-2" onClick={addDogEntry}>Add Dog Entry</button><button className="circle-button" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>i</button>
                        { showMessage && <div style={{ color: 'blue', marginBottom: '10px' }}>Add Dog Entry fills in the previous stake, sire, and dam of the last entry.</div>}
                        <div style={{ overflowX: 'auto' }}>
                            <table className="table table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Number</th>
                                        <th>Stake</th>
                                        <th>Name</th>
                                        <th>Reg Number</th>
                                        <th>Sire</th>
                                        <th>Dam</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {dogEntries.map((dog, index) => (
                                        <tr key={index}>
                                            <td><button className="btn btn-danger" onClick={() => removeDogEntry(index)}>-</button></td>
                                            <td><input type="text" value={dog.number} onChange={e => handleDogEntryChange(index, 'number', e.target.value)} /></td>
                                            <td>
                                                <select 
                                                    value={dog.stake} 
                                                    onChange={e => handleDogEntryChange(index, 'stake', e.target.value)} 
                                                >
                                                    {stakeOptions.map((option) => (
                                                        <option key={option} value={option}>
                                                            {option}
                                                        </option>
                                                    ))}
                                                </select>
                                            </td>                                            <td><input type="text" value={dog.name} onChange={e => handleDogEntryChange(index, 'name', e.target.value)} /></td>
                                            <td><input type="text" value={dog.regNumber} onChange={e => handleDogEntryChange(index, 'regNumber', e.target.value)} /></td>
                                            <td><input type="text" value={dog.sire} onChange={e => handleDogEntryChange(index, 'sire', e.target.value)} /></td>
                                            <td><input type="text" value={dog.dam} onChange={e => handleDogEntryChange(index, 'dam', e.target.value)} /></td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                        <button className="btn btn-primary mt-2" onClick={addDogs}>Submit Dogs</button>
                        <div className="dogs-list mt-3">
                            <table className="table table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th>Number</th>
                                        <th>Name</th>
                                        <th>Stake</th>
                                        <th>Kennel Name</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {dogs.length > 0 ? (
                                        dogs.map(dog => (
                                            <tr key={dog.number}>
                                                <td>{dog.number}</td>
                                                <td>{dog.name}</td>
                                                <td>{dog.stake}</td>
                                                <td>{dog.kennel.owner}</td>
                                                <td>
                                                    <button
                                                        className="btn btn-danger"
                                                        onClick={() => removeDog(dog.number)}
                                                    >Delete
                                                    </button>
                                                </td>
                                            </tr>
                                        ))
                                    ) : (
                                        <tr><td colSpan="5">No dogs available.</td></tr>
                                    )}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            )}
            {selectedStake && !selectedKennel.id && (
                <div className="dog-section" style={{ flex: 2, background: '#c0c0c0', border: '3px solid black', borderRadius: '5px', padding: '10px', minWidth: '1250px' }}>
                    <div className="text-center">
                        <h4 className="medium-text">{selectedStake} Dogs</h4>
                        <div className="dogs-list mt-3">
                            <table className="table table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th>Number</th>
                                        <th>Name</th>
                                        <th>Stake</th>
                                        <th>Kennel Name</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {dogs.length > 0 ? (
                                        dogs.map(dog => (
                                            <tr key={dog.number}>
                                                <td>{dog.number}</td>
                                                <td>{dog.name}</td>
                                                <td>{dog.stake}</td>
                                                <td>{dog.kennel.owner}</td>
                                                <td>
                                                    <button
                                                        className="btn btn-danger"
                                                        onClick={() => removeDog(dog.number)}
                                                    >Delete
                                                    </button>
                                                </td>
                                            </tr>
                                        ))
                                    ) : (
                                        <tr><td colSpan="5">No dogs available.</td></tr>
                                    )}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default DogAndKennelComponent;
