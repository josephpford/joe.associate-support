import { useState, useEffect } from 'react';

export default function useMousePosition(cursorOn) {
	const [mousePosition, setMousePosition] = useState({
		x: 0,
		y: 0,
	});

	function handleMouseMove(event) {
		setMousePosition({ x: event.clientX, y: event.clientY });
	}

	useEffect(() => {
		window.addEventListener('mousemove', handleMouseMove);

		return () => {
			// clean-up portion: what runs when the component gets unmounted
			window.removeEventListener('mousemove', handleMouseMove);
		};
	}, []);

	return mousePosition;
}
