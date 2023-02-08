import { useState } from 'react'
import { useHref } from 'react-router-dom'

export function CreateRoom(props) {
    const [classroomName, setClassroomName] = useState("")
    const [description, setDescription] = useState("")

    // const listUrl = useHref('/')

    const clear = () => {
        setClassroomName("")
        setDescription("")
    }

    const applyResult = (result) => {
        if (result.ok) {
            clear();
        } else {
            window.alert("Nepavyko sukurti: " + result.status)
        }
    }

    const createClassroom = () => {
         fetch('/api/v1/classrooms/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                classroomName,
                description,
            })
        }).then(applyResult)
        // .then(() => window.location = listUrl)
    }

    return (
        <fieldset id="create">
            <legend>Sukurti naują klasę</legend>

            <div>
                <label htmlFor="classroomName">Klasės vardas: </label>
                <input
                    id="classroomName"
                    value={classroomName}
                    onChange={
                        (e) => setClassroomName(e.target.value)
                    } />
            </div>
            <div>
                <label htmlFor="description">Description: </label>
                <textarea
                    id="description"
                    value={description}
                    onChange={
                        (e) => setDescription(e.target.value)
                    } />
            </div>
            <div>
                <button onClick={createClassroom}>Sukurti</button>
            </div>
        </fieldset>
    )
}