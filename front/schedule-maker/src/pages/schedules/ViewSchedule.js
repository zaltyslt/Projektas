import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import { ViewTableHeadline } from './ViewTableHeadline';

// export function ViewSchedule() {
//     const [schedule, setSchedule] = useState([]);
//     const params = useParams();

// useEffect(() => {
//     fetch("api/v1/schedules/" + params.id)
//       .then((response) => response.json())
//       .then((data) => {
//         setSchedule(data);
//       });
//   }, []);

const SCHEDULE_DATA = [
  {
    week: '09.12 - 09.16',
    days: [
      {
        name: 'Pirmadienis',
        lessons: [
          {
            lesson: 'Java pagrindai',
            lecturer: 'Vaclovas',
            room: '202',
            duration: 6,
          },
        ],
      },
      {
        name: 'Antradienis',
        lessons: [
          {
            lesson: 'Java pagrindai',
            lecturer: 'Vaclovas',
            room: '202',
            duration: 6,
          },
        ],
      },
    ],
  },
  {
    week: '09.19 - 09.23',
    days: [
      {
        name: 'Pirmadienis',
        lessons: [
          {
            lesson: 'Java pagrindai',
            lecturer: 'Vaclovas',
            room: '202',
            duration: 6,
          },
        ],
      },
      {
        name: 'Antradienis',
        lessons: [
          {
            lesson: 'Operacines sistemos',
            lecturer: 'Rita',
            room: '200',
            duration: 6,
          },
        ],
      },
    ],
  },
  {
    week: '09.25 - 09.29',
    days: [
      {
        name: 'Pirmadienis',
        lessons: [
          {
            lesson: 'Java pagrindai',
            lecturer: 'Vaclovas',
            room: '202',
            duration: 6,
          },
        ],
      },
      {
        name: 'Antradienis',
        lessons: [
          {
            lesson: 'Operacines sistemos',
            lecturer: 'Rita',
            room: '200',
            duration: 6,
          },
        ],
      },
    ],
  },
];

export const ViewSchedule = () => {
  return (
    <StyledTable>
      <ViewTableHeadline />
    </StyledTable>
  );
};

const StyledTable = styled.table`
  border: 1px solid black;
  th,
  td {
    border: 1px solid black;
  }
`;
