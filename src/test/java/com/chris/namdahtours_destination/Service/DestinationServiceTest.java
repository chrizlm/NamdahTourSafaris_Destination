package com.chris.namdahtours_destination.Service;

import com.chris.namdahtours_destination.Model.Destination;
import com.chris.namdahtours_destination.Repository.DestinationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.InstanceNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DestinationServiceTest {

    @Mock private DestinationRepository destinationRepository;
    private DestinationService destinationServiceUnderTest;

    @BeforeEach
    void setUp() {
        destinationServiceUnderTest = new DestinationService(destinationRepository);
    }

    @Test
    void addDestination() {
        //given
        Destination destination = new Destination(
          1,
          "masai mara",
          "kenya"
        );

        //when
        destinationServiceUnderTest.addDestination(destination);

        //then
        ArgumentCaptor<Destination> destinationArgumentCaptor = ArgumentCaptor.forClass(Destination.class);
        verify(destinationRepository).save(destinationArgumentCaptor.capture());

        Destination capturedDestination = destinationArgumentCaptor.getValue();

        assertThat(capturedDestination).isEqualTo(destination);
    }

    @Test
    void getDestination() throws InstanceNotFoundException {
        //given
        int id = 1;
        Destination destination = new Destination(
                id,
                "masai mara",
                "kenya"
        );

        given(destinationRepository.findById(id)).willReturn(Optional.of(destination));

        //when
        Destination expectedDestination = destinationServiceUnderTest.getDestination(id);

        //then
        assertThat(expectedDestination).isEqualTo(destination);
    }

    @Test
    void getAllDestinations() {
        //given
        //when
        destinationServiceUnderTest.getAllDestinations();
        //then
        verify(destinationRepository).findAll();
    }

    @Test
    void updateDestination() throws InstanceNotFoundException {
        //given
        int id = 1;
        String new_name = "kilimanjaro";
        String new_location = "tanzania";
        Destination destination = new Destination(
                id,
                "masai mara",
                "kenya"
        );

        given(destinationRepository.findById(id)).willReturn(Optional.of(destination));

        //when
        destinationServiceUnderTest.updateDestination(id, new_name, new_location);

        //then
        Optional<Destination> expectedDestination = destinationRepository.findById(id);

        assertThat(expectedDestination).isNotEqualTo(destination);
        assertThat(expectedDestination.get().getName()).isEqualTo(new_name);
        assertThat(expectedDestination.get().getLocation()).isEqualTo(new_location);

    }

    @Test
    void deleteDestination() throws InstanceNotFoundException {
        //given
        int id = 1;
        given(destinationRepository.existsById(id)).willReturn(true);

        //when
        destinationServiceUnderTest.deleteDestination(id);
        //then
        verify(destinationRepository).deleteById(id);
    }

    @Test
    void willThrowExceptionOnDelete(){
        //given
        int id = 1;
        given(destinationRepository.existsById(id)).willReturn(false);

        //when
        //then
        assertThatThrownBy(()-> destinationServiceUnderTest.deleteDestination(id))
                .isInstanceOf(InstanceNotFoundException.class)
                .hasMessageContaining("destination with id not found");

        verify(destinationRepository, never()).deleteById(id);
    }
}