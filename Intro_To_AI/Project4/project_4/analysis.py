from Testing import *

output = []
for i in range(5):
    print("iteration %s:" % i)
    nnet, accuracy = testPenData()
    output.append(accuracy)
print("PenTest finished")
print("Max: %f" % max(output))
print("Average: %f" % average(output))
print("Standard Deviation: %f" % stDeviation(output))

output = []
for i in range(5):
    print("iteration %s:" % i)
    nnet, accuracy = testCarData()
    output.append(accuracy)
print("CarTest finished")
print("Max: %f" % max(output))
print("Average: %f" % average(output))
print("Standard Deviation: %f" % stDeviation(output))


