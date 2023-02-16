import { useEffect, useState } from "react"

export function Buildings(props) {
    const [buildings, setBuildings] = useState([])
    const [selectedBuilding, setSelectedBuilding] = useState(null)

    useEffect(() => {
        fetch('/api/v1/buildings')
            .then(response => response.json())
            .then(setBuildings)
    }, [])

    const assignClassToBuilding = () => {
        fetch(`/api/v1/buildings/${props.id}/addbuilding?buildingId=${selectedBuilding}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => response.json())
        .then((classroom) => props.onClassroomChange(classroom))
    }

    return(
        <div>
            <select value={selectedBuilding} onChange={
                (e) => setSelectedBuilding(e.target.value)
            }>
                <option>...</option>
                {
                    buildings.map((building) => 
                    <option 
                    key={building.id}
                        value={building.id}>
                            {building.name}</option>)
                }
            </select>
            <button onClick={assignClassToBuilding}>Priskirti</button>
        </div>
    )
}