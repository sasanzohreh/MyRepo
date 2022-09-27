from Testing import *

for i in range(0, 45, 5):
    output = []
    for j in range(5):
        print("Iteration %d of layer %d for CarTest" % (j, i))
        nnet, accuracy = testCarData(hiddenLayers=[i])
        output.append(accuracy)
    print("CarTest finished")
    print("Max: %f" % max(output))
    print("Average: %f" % average(output))
    print("Standard Deviation: %f" % stDeviation(output))

for i in range(0, 45, 5):
    output = []
    for j in range(5):
        print("Iteration %d of layer %d for PenTest" % (j, i))
        nnet, accuracy = testPenData(hiddenLayers=[i])
        output.append(accuracy)
    print("PenTest finished")
    print("Max: %f" % max(output))
    print("Average: %f" % average(output))
    print("Standard Deviation: %f" % stDeviation(output))