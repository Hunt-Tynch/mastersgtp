import { useEffect, useState } from "react";
import { deleteJudge, getJudges, postJudge } from "../services/JudgeService";
import { deleteScore, getAllJudgeScores } from "../services/ScoreService";

const JudgeComponent = () => {
    const [judges, setJudges] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [selectedJudge, setSelectedJudge] = useState({ id: null });
    const [selected, setSelected] = useState(false)
    const [number, setNumber] = useState("")
    const [name, setName] = useState("")
    const [memberPIN, setMemberPIN] = useState("")
    const [error, setError] = useState(null)
    const [success, setSuccess] = useState(null)
    const [scores, setScores] = useState([])

    useEffect(() => {
        getJudges().then(ret => {
            setJudges(ret.data);
        }).catch(err => {
            console.log(err);
        });
    }, []);

    const filteredJudges = judges.filter(judge => {
        if (!searchTerm || isNaN(searchTerm)) {
            return judge.name.toLowerCase().includes(searchTerm.toLowerCase());
        } else {
            return judge.id === Number(searchTerm);
        }
    });

    const handleSelect = (judge) => {
        setSelectedJudge(judge);
        getAllJudgeScores(judge.id).then(ret => {
            setScores(ret.data)
        }).catch(err => {
            console.log(err)
        })
        setSearchTerm(""); // Clear the search input after selection
    };

    const addJudge = () => {
        if (!number || !name) { setError('Must have number and name'); setSuccess(null); return }

        postJudge({ id: number, name: name, memberPIN: memberPIN }).then(() => {
            getJudges().then(ret => {
                setJudges(ret.data);
            }).catch(err => {
                console.log(err);
            });
            setError(null)
            setSuccess('Added Judge')
            setNumber("")
            setName("")
            setMemberPIN("")
        }).catch(err => {
            setError('Judge exists with that number already.')
            setSuccess(null)
        })
    }

    const handleDelete = () => {
        if (!selectedJudge.id) return
        deleteJudge(selectedJudge.id).then(() => {
            getJudges().then(ret => {
                setJudges(ret.data);
            }).catch(err => {
                console.log(err);
            });
            setSelectedJudge({ id: null });
        }).catch(err => {
            console.log(err);
        });
    };

    const handleDeleteScore = (id) => {
        deleteScore(id).then(() => {
            getAllJudgeScores(selectedJudge.id).then(ret => {
                setScores(ret.data)
            }).catch(err => {
                console.log(err)
            })
        }).catch(err => {
            console.log(err)
        })
    }

    return (
        <div className="container d-flex justify-content-center align-items-start" style={{overflowX: '100vw', minWidth: '100vw', minHeight: '100vh'}}>
            <div className="judge-section" style={{ flex: 1, background: '#c0c0c0', border: '3px solid black', borderRadius: '5px', padding: '10px' }}>
                <div className="text-center">
                    <div className="large-text">Judges</div>
                    <div style={{ position: 'relative', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        (click search bar to clear selected judge)
                        <input
                            type="text"
                            placeholder="Search Judges by Name or Id"
                            value={searchTerm}
                            onChange={e => setSearchTerm(e.target.value)}
                            onSelect={() => { setSelectedJudge({ id: null }); setSelected(true); }}
                            onBlur={() => { setTimeout(() => {setSelected(false) }, 200) }}
                            style={{ width: '250px' }} // Margin to create space between input and dropdown
                        />
                        {filteredJudges.length > 0 && (!selectedJudge.id && selected) && ( // Only show dropdown if no judge is selected
                            <div className="dropdown-menu" style={{ 
                                position: 'absolute', 
                                top: '100%', 
                                backgroundColor: 'white', 
                                border: '1px solid #ccc', 
                                zIndex: 1, 
                                width: '250px' 
                            }}>
                                {filteredJudges.map((judge) => (
                                    <div 
                                        key={judge.id} 
                                        className="dropdown-item" 
                                        style={{
                                            cursor: 'pointer', 
                                            border: '1px solid black', 
                                            padding: '5px' 
                                        }}
                                        onClick={() => handleSelect(judge)}
                                    >
                                        <p style={{ margin: 0 }}>#{judge.id}, {judge.name}</p>
                                    </div>
                                ))}
                            </div>
                        )}
                        {filteredJudges.length === 0 && searchTerm && <p>No Judges available.</p>}
                    </div>
                    {selectedJudge.id && (
                        <div className="container d-flex flex-column justify-content-center align-items-center" style={{marginTop: '30px'}}>
                            <div className="medium-text" style={{marginBottom: '15px'}}>Selected Judge: <br /><div className="medium-text" style={{background: 'yellow', maxWidth: '600px'}}>#{selectedJudge.id}, {selectedJudge.name}</div></div>
                            <button className="btn btn-danger" onClick={handleDelete}>Delete</button>
                            <div className="d-flex justify-content-center">
                                <div style={{minWidth:'400px', marginRight: '100px'}}>
                                    <h1 className="large-text">Scores</h1>
                                    <div style={{overflowY: 'auto', maxHeight: '60vh'}}>
                                    <table className="table table-striped table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Time</th>
                                                <th>Dogs</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {scores.map(score => (
                                                <tr key={score.id}>
                                                    <td>{score.time}</td>
                                                    <td>
                                                        {score.firstDog.number}<br/>
                                                        {score.secondDog && score.secondDog.number}<br/>
                                                        {score.thirdDog && score.thirdDog.number}<br/>
                                                        {score.fourthDog && score.fourthDog.number}<br/>
                                                        {score.fifthDog && score.fifthDog.number}
                                                    </td>
                                                    <td>
                                                        <button className="btn btn-secondary">Edit</button>
                                                        <button className="btn btn-danger" onClick={() => handleDeleteScore(score.id)}>Delete</button>
                                                    </td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>
                                    </div>
                                </div>
                                <div style={{minWidth:'400px'}}>
                                    <h1 className="large-text">Scratches</h1>
                                    <table className="table table-striped table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Time</th>
                                                <th>Dog</th>
                                                <th>Reason</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    )}
                    {!selectedJudge.id && !selected &&
                        <div className="container d-flex flex-column" style={{maxWidth: '400px'}}>
                            <p className="large-text" style={{ marginTop: '50px' }}>Add Judge</p>
                            {error && <div className="alert alert-danger">{error}</div>}
                            {success && <div className="alert alert-success">{success}</div>}
                            <input type="number" value={ number } onChange={e => setNumber(e.target.value)} placeholder="Number"></input>
                            <input type="text" value={ name } onChange={e => setName(e.target.value)} placeholder="Name"></input>
                            <input type="text" value={ memberPIN } onChange={e => setMemberPIN(e.target.value)} placeholder="Member PIN"></input>
                            <button type="btn btn-secondary" onClick={ addJudge } style={{marginTop: '10px', background: 'lightgray'}}>Add Judge</button>
                        </div>
                    }
                </div>
            </div>
        </div>
    );
};

export default JudgeComponent;
